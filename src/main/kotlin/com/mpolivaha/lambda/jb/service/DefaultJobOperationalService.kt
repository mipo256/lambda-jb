package com.mpolivaha.lambda.jb.service

import com.mpolivaha.lambda.jb.domain.JobDefinition
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.persistence.EntityManager
import jakarta.persistence.LockModeType
import jakarta.persistence.PersistenceContext
import jakarta.persistence.PersistenceContextType
import jakarta.persistence.SynchronizationType
import jakarta.transaction.Transactional
import org.hibernate.Session
import java.util.*

@ApplicationScoped
class DefaultJobOperationalService @Inject constructor(
    @PersistenceContext(type = PersistenceContextType.TRANSACTION, synchronization = SynchronizationType.SYNCHRONIZED)
    private val entityManager: EntityManager,
) : JobOperationalService {

    // language=sql
    companion object {
        private const val LOAD_FULLY : String = "SELECT jd FROM JobDefinition jd LEFT JOIN FETCH jd.jobExecutions WHERE jd.id = :jobId"

        private const val FIND_ELIGIBLE_FOR_PROCESSING : String = """
            -- Why not window function? Man, idk
            WITH with_last_exec AS (
                SELECT 
                    je.job_definition_id AS jdi,
                    MAX(je.executed_at) AS last_exec,
                    COUNT(je.executed_at) AS exec_cnt
                FROM job_definitions AS jd
                LEFT JOIN job_executions AS je ON jd.id = je.job_definition_id
                GROUP BY je.job_definition_id
            )
            SELECT * 
            FROM job_definitions AS jd 
            INNER JOIN with_last_exec AS wle ON wle.jdi = jd.id
            WHERE 
                -- either the time elapsed from last execution was longer then delay interval
                (wle.last_exec IS NOT NULL AND wle.last_exec + EXTRACT(EPOCH FROM jd.execution_interval) <= EXTRACT(EPOCH FROM NOW()))
                OR
                -- we do not have any executions yet, but the time for the scheduled first execution came
                (wle.last_exec IS NULL AND jd.first_scheduled_execution_timestamp <= EXTRACT(EPOCH FROM NOW()))
            -- we sort by exec_cnt to account for job starvation phenomenon that can occur in case we have a lot of high priority jobs 
            ORDER BY wle.exec_cnt ASC, jd.priority DESC, jd.created_at ASC
            """
    }

    @Transactional
    override fun createJob(job: JobDefinition): JobDefinition {
        entityManager.persist(job)
        return job
    }

    @Transactional
    override fun loadFullById(id: UUID) : JobDefinition {
        val query = entityManager.unwrap(Session::class.java).createQuery(LOAD_FULLY, JobDefinition::class.java)
        query.setParameter("jobId", id)
        return query.singleResult
    }

    /**
     * There is, theoretically, a possibility to use [org.hibernate.ScrollableResults] here instead of [List] as a return type.
     *
     * But it does not seem to make much sense since:
     *
     * * We're going to have to hold a transaction for quite a long time, which is bad in every possible regard
     * * In order to process elements efficiently, it seems that we can just use trivial [LIMIT SQL clause] [org.hibernate.query.Query.setMaxResults]
     *   and that would be sufficient.
     */
    @Suppress("UNCHECKED_CAST")
    override fun loadReadyForProcessing(batchSize: Int): List<JobDefinition> {
        val query = entityManager.unwrap(Session::class.java).createNativeQuery(FIND_ELIGIBLE_FOR_PROCESSING, JobDefinition::class.java)
        query.lockMode = LockModeType.PESSIMISTIC_WRITE
        query.maxResults = batchSize
        return query.resultList as List<JobDefinition>
    }
}
