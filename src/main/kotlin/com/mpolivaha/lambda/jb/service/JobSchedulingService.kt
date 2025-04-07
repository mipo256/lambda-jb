package com.mpolivaha.lambda.jb.service

import com.mpolivaha.lambda.jb.domain.JobDefinition

/**
 *
 * @author Mikhail Polivakha
 */
interface JobSchedulingService {

    /**
     * Initially schedules the given [JobDefinition] for its first (and, potentially, the only) execution.
     *
     * @return the fully scheduled [JobDefinition]
     */
    fun scheduleJobs()
}
