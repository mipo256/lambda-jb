package com.mpolivaha.lambda.jb.service

import com.mpolivaha.lambda.jb.domain.JobDefinition
import com.mpolivaha.lambda.jb.domain.JobExecution
import java.util.UUID

interface JobOperationalService {

    /**
     * Initially schedules the given [JobDefinition] for its first (and, potentially, the only) execution.
     *
     * @return the fully scheduled [JobDefinition]
     */
    fun createJob(job: JobDefinition) : JobDefinition

    /**
     * Load [JobDefinition] fully (meaning, including it's [executions][JobExecution]) by its id.
     */
    fun loadFullById(id: UUID) : JobDefinition

    /**
     * Load flat version of [JobDefinition]
     */
    fun loadReadyForProcessing(batchSize: Int) : List<JobDefinition>
}
