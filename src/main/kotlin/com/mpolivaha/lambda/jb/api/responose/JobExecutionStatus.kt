package com.mpolivaha.lambda.jb.api.responose

/**
 * The result of the execution of the given Job as an OS process.
 *
 * @author Mikhail Polivakha
 */
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