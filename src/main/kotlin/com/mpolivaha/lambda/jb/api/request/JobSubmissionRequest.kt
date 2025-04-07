package com.mpolivaha.lambda.jb.api.request

import java.time.Duration

/**
 * Format of the [Duration] data types is a standard ISO-8601 format.
 *
 * @param data compiled java bytecode to be executed
 * @param executionInterval the interval between executions. If the interval is not present, then the job
 *        is considered to be ad-hoc.
 */
class JobSubmissionRequest(
    val priority: Int,
    val data: ByteArray,
    val javaVersion: Int,
    val executionInterval: Duration?,
    val initialDelay: Duration,

    /**
     * Includes:
     *
     * - Standard Options
     * - Non-Standard Options
     * - Advanced Runtime Options etc
     */
    val vmOptions: List<String>,
    val envVariables: List<String>
)
