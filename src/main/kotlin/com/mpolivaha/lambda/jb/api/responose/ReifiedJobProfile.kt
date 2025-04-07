package com.mpolivaha.lambda.jb.api.responose

import java.time.Instant
import java.util.UUID

/**
 * Reified [com.mpolivaha.lambda.jb.domain.JobDefinition] profiles, including its executions.
 *
 * @author Mikhail Polivakha
 */
data class ReifiedJobProfile(
    val id: UUID,
    val createdAt: Instant,
    val executions: List<ReifiedJobExecution>
) {

    data class ReifiedJobExecution(
        val stdout: String?,
        val stderr: String?,
        val jobExecutionStatus: JobExecutionStatus,
        val executedAt: Instant,
    )
}