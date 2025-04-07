package com.mpolivaha.lambda.jb.domain

enum class JobStatus {

    /**
     * The job was scheduled
     */
    SCHEDULED,

    /**
     * The job is in the process of execution
     */
    PROCESSING,

    /**
     * Executed. Either successfully (process returned 0) or not (process returned non-0 status code)
     */
    EXECUTED,

    /**
     * In case of any internal error occurred during any stage in the overall pipeline
     */
    ERROR
}