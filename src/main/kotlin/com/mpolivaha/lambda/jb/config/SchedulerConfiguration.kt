package com.mpolivaha.lambda.jb.config

import com.mpolivaha.lambda.jb.service.JobSchedulingService
import jakarta.annotation.PostConstruct
import jakarta.inject.Inject
import jakarta.inject.Singleton
import org.eclipse.microprofile.config.inject.ConfigProperty
import java.time.Duration
import java.util.*

@Singleton
open class SchedulerConfiguration(
    @ConfigProperty(name = "job.scheduler.pickup.interval.ms")
    private val jobSchedulerPickUpInterval : Long,

    @Inject
    private val jobSchedulingService: JobSchedulingService
) {

    @PostConstruct
    open fun init() {
        Timer().schedule(
            object : TimerTask() {
                override fun run() {
                    jobSchedulingService.scheduleJobs()
                }
            },
            Duration.ofMillis(jobSchedulerPickUpInterval).toMillis()
        )
    }
}
