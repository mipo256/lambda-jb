package com.mpolivaha.lambda.jb.domain

import com.mpolivaha.lambda.jb.domain.extension.PostgreSQLIntervalType
import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.annotations.Type
import java.sql.Types
import java.time.Duration
import java.time.Instant

/**
 *
 * @author Mikhail Polivakha
 */
@Embeddable
open class JobExecutionSchedule(

    @JdbcTypeCode(Types.BIGINT)
    @Column(name = "first_scheduled_execution_timestamp")
    open val firstScheduledExecutionTimestamp: Instant,

    @Type(PostgreSQLIntervalType::class)
    @Column(name = "execution_interval")
    open val executionInterval: Duration? = null
)
