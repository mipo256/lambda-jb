# Jet Brains Test Project - Lambda.

Service is supposed to be deployed in orchestration env. or as a separate Java process. The requirement is to have the 
container runtime env. (`containerd` for instance). The reason for this is because of the security concerns, the jobs
are launched as a separated isolated processes in the form of a containers (most probably `docker` as a front-end).

It also has the following endpoints:

## POST /api/jobs/v1
Schedules the job to the execution. Current implementation just saves the job definition into the database. 
Later on this job is going to be scheduled for execution in the isolated linux process/container. 

## GET /api/jobs/v1/{id}
Gets the meta-information about the execution of the given job.

The service written using technologies:

1. Quarkus
2. Hibernate 
3. Containerd client
