package com.mpolivaha.lambda.jb.service

import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import org.eclipse.microprofile.config.inject.ConfigProperty

@ApplicationScoped
class ContainerizedJobSchedulingService @Inject constructor(
    @ConfigProperty(name = "job.processing.batch.size", defaultValue = "10")
    private val jobProcessingBatchSize: Int,
    private val jobOperationalService: JobOperationalService
) : JobSchedulingService {

    override fun scheduleJobs() {
        val jobDefinitions = jobOperationalService.loadReadyForProcessing(jobProcessingBatchSize)

    }

}
