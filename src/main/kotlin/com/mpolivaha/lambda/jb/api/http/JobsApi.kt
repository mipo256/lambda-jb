package com.mpolivaha.lambda.jb.api.http

import com.mpolivaha.lambda.jb.api.request.JobSubmissionRequest
import com.mpolivaha.lambda.jb.api.responose.JobScheduledResponse
import com.mpolivaha.lambda.jb.api.responose.ReifiedJobProfile
import com.mpolivaha.lambda.jb.mapping.JobDefinitionMapper
import com.mpolivaha.lambda.jb.service.JobOperationalService
import com.mpolivaha.lambda.jb.service.JobSchedulingService
import jakarta.inject.Inject
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.GET
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import org.jboss.resteasy.reactive.RestPath
import org.jboss.resteasy.reactive.RestResponse
import java.util.*

@Path("/api/jobs/v1")
class JobsApi @Inject constructor(
    private val jobsSchedulingService: JobSchedulingService,
    private val jobOperationalService: JobOperationalService,
    private val jobDefinitionMapper: JobDefinitionMapper
) {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    fun submit(request: JobSubmissionRequest) : RestResponse<JobScheduledResponse> {
        val jobDefinition = jobDefinitionMapper.fromHttpRequest(request)
        val scheduledJobDefinition = jobOperationalService.createJob(jobDefinition)
        val scheduledResponse = jobDefinitionMapper.toScheduledResponse(scheduledJobDefinition)
        return RestResponse.ResponseBuilder.create(RestResponse.Status.CREATED, scheduledResponse).build()
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    fun getJobProfile(@RestPath("id") jobId: UUID) : RestResponse<ReifiedJobProfile> {
        val jobDefinition = jobOperationalService.loadFullById(jobId)
        val reifiedProfile = jobDefinitionMapper.toReifiedProfile(jobDefinition)
        return RestResponse.ok(reifiedProfile)
    }
}
