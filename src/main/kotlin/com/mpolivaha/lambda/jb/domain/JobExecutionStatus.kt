package com.mpolivaha.lambda.jb.domain

enum class JobExecutionStatus {

    /**
     * Process returned 0
     */
    SUCCESS,

    /**
     * Process returned non-0 status code
     */
    FAILURE
}