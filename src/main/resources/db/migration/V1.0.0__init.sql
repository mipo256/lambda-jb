CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS job_definitions(
    id UUID PRIMARY KEY,
    data BYTEA NOT NULL,
    java_version SMALLINT NOT NULL,
    created_at BIGINT NOT NULL,
    priority BIGINT NOT NULL CONSTRAINT priority_boundaries_check CHECK(priority > 0 AND priority < 11),
    job_status SMALLINT NOT NULL CONSTRAINT job_status_correctness_check CHECK(job_status >= 0 AND job_status < 4),
    first_scheduled_execution_timestamp BIGINT NOT NULL,
    execution_interval INTERVAL NOT NULL,
    vm_options TEXT ARRAY NOT NULL,
    env_variables TEXT ARRAY NOT NULL
);

CREATE TABLE IF NOT EXISTS job_executions(
    id UUID PRIMARY KEY,
    stdout TEXT,
    stderr TEXT,
    job_execution_status SMALLINT NOT NULL CONSTRAINT job_execution_status_correctness_check CHECK(job_execution_status >= 0 AND job_execution_status < 2),
    executed_at BIGINT,
    job_definition_id UUID REFERENCES job_definitions(id) DEFERRABLE INITIALLY IMMEDIATE
)
