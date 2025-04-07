package com.mpolivaha.lambda.jb.service

import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject

@ApplicationScoped
class JobPickingService @Inject constructor(
    private val jobExecutionService: JobExecutionService
) {

    init {

    }

    fun execute() {

    }
}