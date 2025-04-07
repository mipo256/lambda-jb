package com.mpolivaha.lambda.jb.service

import com.github.dockerjava.api.DockerClient
import com.mpolivaha.lambda.jb.domain.JobDefinition
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.inject.Singleton

@ApplicationScoped
class DockerEngineBasedExecutionService @Inject constructor(
    private val dockerClient: DockerClient
): JobExecutionService {

    override fun execute(job: JobDefinition) {

    }
}