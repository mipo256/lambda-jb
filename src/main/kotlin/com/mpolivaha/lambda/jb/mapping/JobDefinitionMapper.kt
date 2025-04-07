package com.mpolivaha.lambda.jb.mapping

import com.mpolivaha.lambda.jb.api.request.JobSubmissionRequest
import com.mpolivaha.lambda.jb.api.responose.JobScheduledResponse
import com.mpolivaha.lambda.jb.api.responose.ReifiedJobProfile
import com.mpolivaha.lambda.jb.api.responose.ReifiedJobProfile.ReifiedJobExecution
import com.mpolivaha.lambda.jb.domain.JobDefinition
import com.mpolivaha.lambda.jb.domain.JobExecutionSchedule
import com.mpolivaha.lambda.jb.domain.JobStatus
import com.mpolivaha.lambda.jb.domain.ProcessLaunchContext
import jakarta.enterprise.context.ApplicationScoped
import java.time.Instant
import java.util.*

typealias JobExecutionStatusDto = com.mpolivaha.lambda.jb.api.responose.JobExecutionStatus

/**
 * Mapper aimed at mapping into/from [JobDefinition].
 *
 * @author Mikhail Polivakha
 */
@ApplicationScoped
class JobDefinitionMapper {

    /**
     * Build [JobDefinition] from [JobSubmissionRequest].
     *
     * @return built [JobDefinition]
     */
    fun fromHttpRequest(request: JobSubmissionRequest) : JobDefinition {
        val currentTimestamp = Instant.now()

        return JobDefinition(
            id = UUID.randomUUID(),
            data = request.data,
            javaVersion = request.javaVersion,
            createdAt = currentTimestamp,
            priority = request.priority,
            jobStatus = JobStatus.SCHEDULED,
            jobExecutionSchedule = JobExecutionSchedule(
                firstScheduledExecutionTimestamp = currentTimestamp.plus(request.initialDelay),
                executionInterval = request.executionInterval,
            ),
            processLaunchContext = ProcessLaunchContext(
                vmOptions = request.vmOptions,
                envVariables = request.envVariables,
            ),
            jobExecutions = mutableListOf()
        )
    }

    /**
     * Build [JobScheduledResponse] from [JobDefinition]. The passed [JobDefinition] si required to be already scheduled.
     *
     * @return built [JobDefinition]
     */
    fun toScheduledResponse(jobDefinition: JobDefinition) : JobScheduledResponse {
        return JobScheduledResponse(
            id = jobDefinition.id!!,
            createdAt = jobDefinition.createdAt,
            firstExecutionTimestamp = jobDefinition.jobExecutionSchedule.firstScheduledExecutionTimestamp
        )
    }

    /**
     * Map [JobDefinition] to its [reified profile] [ReifiedJobExecution]
     */
    fun toReifiedProfile(jobDefinition: JobDefinition) : ReifiedJobProfile {
        return ReifiedJobProfile(
            id = jobDefinition.id!!,
            createdAt = jobDefinition.createdAt,
            executions = jobDefinition.jobExecutions.map {
                ReifiedJobExecution(
                    stdout = it.stdout,
                    stderr = it.stderr,
                    jobExecutionStatus = JobExecutionStatusDto.valueOf(it.jobExecutionStatus.name),
                    executedAt = it.executedAt
                )
            }
        )
    }
}
