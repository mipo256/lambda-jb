package com.mpolivaha.lambda.jb.domain

import com.mpolivaha.lambda.jb.exception.IllegalJobDefinitionException
import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import org.hibernate.annotations.JdbcTypeCode
import java.sql.Types
import java.time.Instant
import java.util.*

/**
 * The Java Job to be executed.
 *
 * @author Mikhail Polivakha
 */
@Entity
@Table(name = "job_definitions")
open class JobDefinition(

    @Id
    @Column(name = "id")
    open var id: UUID? = null,

    @Column(name = "data", nullable = false)
    open var data: ByteArray,

    /**
     * Version of the Java Platform.
     */
    @Column(name = "java_version")
    open var javaVersion: Int,

    @JdbcTypeCode(Types.BIGINT)
    @Column(name = "created_at", nullable = false)
    open var createdAt: Instant,

    /**
     * Priority of the job. Integer value from 1 to 10.
     */
    @Column(name = "priority", nullable = false)
    open val priority: Int,

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "job_status", nullable = false)
    open val jobStatus: JobStatus,

    @Embedded
    open val jobExecutionSchedule: JobExecutionSchedule,

    @Embedded
    open val processLaunchContext: ProcessLaunchContext,

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "jobDefinition")
    open val jobExecutions: MutableList<JobExecution>,
) {

    init {
        if (priority < 1 || priority > 10) {
            throw IllegalJobDefinitionException("Priority $priority is not in allowed range [1-10]")
        }

        if (javaVersion < 5 || javaVersion > 17) {
            throw IllegalJobDefinitionException("Java version $javaVersion is not in allowed range [5-17]")
        }
    }

    fun isAdHoc() = this.jobExecutionSchedule.executionInterval == null

    override fun toString(): String {
        return "JobDefinition(id=$id, data=${data.contentToString()}, javaVersion=$javaVersion, createdAt=$createdAt, priority=$priority, jobExecutionSchedule=$jobExecutionSchedule, processLaunchContext=$processLaunchContext)"
    }
}
