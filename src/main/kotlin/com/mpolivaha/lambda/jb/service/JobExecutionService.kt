package com.mpolivaha.lambda.jb.service

import com.mpolivaha.lambda.jb.domain.JobDefinition

interface JobExecutionService {

    fun execute(job: JobDefinition)
}