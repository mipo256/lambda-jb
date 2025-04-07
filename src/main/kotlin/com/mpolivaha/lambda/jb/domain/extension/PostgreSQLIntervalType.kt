package com.mpolivaha.lambda.jb.domain.extension

import org.hibernate.engine.spi.SharedSessionContractImplementor
import org.hibernate.usertype.UserType
import org.postgresql.util.PGInterval
import java.io.Serializable
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Types
import java.time.Duration
import java.time.temporal.ChronoUnit
import java.util.*
import kotlin.math.roundToLong

class PostgreSQLIntervalType : UserType<Duration> {

    companion object {
        private const val MICROS_IN_SECOND = 1000000
    }

    override fun getSqlType(): Int = Types.OTHER

    override fun returnedClass(): Class<Duration> = Duration::class.java

    override fun equals(x: Duration?, y: Duration?): Boolean = Objects.equals(x, y)

    override fun hashCode(x: Duration?): Int = x.hashCode()

    override fun nullSafeGet(rs: ResultSet?, position: Int, session: SharedSessionContractImplementor?, owner: Any?): Duration? {
        val interval = rs?.getObject(position) as? PGInterval ?: return null

        val days = interval.days.toLong()
        val hours = interval.hours.toLong()
        val minutes = interval.minutes.toLong()
        val seconds = interval.seconds.toLong()
        val micros = ((interval.seconds - seconds) * MICROS_IN_SECOND).roundToLong()

        return Duration.ofDays(days)
            .plus(hours, ChronoUnit.HOURS)
            .plus(minutes, ChronoUnit.MINUTES)
            .plus(seconds, ChronoUnit.SECONDS)
            .plus(micros, ChronoUnit.MICROS);
    }

    override fun isMutable(): Boolean = true

    override fun assemble(cached: Serializable?, owner: Any?): Duration {
        return cached as Duration
    }

    override fun disassemble(value: Duration?): Serializable? {
        return value
    }

    override fun deepCopy(value: Duration?): Duration? {
        return Duration.ofSeconds(value?.toSeconds() ?: return null)
    }

    override fun nullSafeSet(st: PreparedStatement, value: Duration?, index: Int, session: SharedSessionContractImplementor?) {
        if (value == null) {
            st.setNull(index, Types.OTHER)
        } else {
            val days = value.toDays().toInt()
            val hours = (value.toHours() % 24).toInt()
            val minutes = (value.toMinutes() % 60).toInt()
            val seconds = (value.seconds % 60).toInt()
            val micros = value.nano / 1000
            val secondsWithFraction = (seconds + micros / MICROS_IN_SECOND).toDouble()
            st.setObject(index, PGInterval(0, 0, days, hours, minutes, secondsWithFraction))
        }
    }
}