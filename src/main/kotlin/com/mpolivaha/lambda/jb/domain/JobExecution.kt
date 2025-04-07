package com.mpolivaha.lambda.jb.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.hibernate.annotations.JdbcTypeCode
import java.sql.Types
import java.time.Instant
import java.util.*

/**
 * Executions of the given [JobDefinition].
 *
 * @author Mikhail Polivakha
 */
@Entity
@Table(name = "job_executions")
open class JobExecution(

    @Id
    open var id: UUID? = null,

    @Column(name = "stdout")
    open val stdout: String?,

    @Column(name = "stderr")
    open val stderr: String?,

    @Column(name = "job_execution_status", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    open val jobExecutionStatus: JobExecutionStatus,

    @JdbcTypeCode(Types.BIGINT)
    @Column(name = "executed_at")
    open val executedAt: Instant,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_definition_id")
    open val jobDefinition: JobDefinition
) {

    override fun toString(): String {
        return "JobExecution(id=$id, stdout='$stdout', stderr='$stderr', jobExecutionStatus=$jobExecutionStatus, executedAt=$executedAt)"
    }
}
