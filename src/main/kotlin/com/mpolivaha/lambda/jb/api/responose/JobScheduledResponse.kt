package com.mpolivaha.lambda.jb.api.responose

import java.time.Instant
import java.util.UUID

/**
 * The refined description of the [com.mpolivaha.lambda.jb.domain.JobDefinition].
 * Typically, this is the response to the [com.mpolivaha.lambda.jb.api.request.JobSubmissionRequest].
 * Contains information regarding the scheduling of the supplied JobDefinition.
 *
 * @author Mikhail Polivakha
 */
class JobScheduledResponse(
    var id: UUID,
    var createdAt: Instant,
    val firstExecutionTimestamp: Instant
)