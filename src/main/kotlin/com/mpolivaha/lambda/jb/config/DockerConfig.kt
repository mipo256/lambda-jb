package com.mpolivaha.lambda.jb.config

import com.github.dockerjava.api.DockerClient
import com.github.dockerjava.core.DockerClientBuilder
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.inject.Produces

@ApplicationScoped
class DockerConfig {

    @Produces
    fun dockerClient() : DockerClient {
        return DockerClientBuilder.getInstance().build()
    }
}