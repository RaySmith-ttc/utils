package ru.raysmith.utils

import java.time.*
import java.util.*

val timeZone = (System.getProperty("TIME_ZONE") ?: System.getenv("TIME_ZONE"))?.let { ZoneId.of(it) }
    ?: ZoneId.systemDefault()

/**
 * Returns the current date and time in the time zone specified in the system property or environment variable `TIME_ZONE`.
 * If the variable is not specified, the system default time zone is used.
 */
fun now(): LocalDateTime = LocalDateTime.now(timeZone)

/**
 * Returns the current time in the time zone specified in the system property or environment variable `TIME_ZONE`.
 * If the variable is not specified, the system default time zone is used.
 */
fun nowTime(): LocalTime = now().toLocalTime()

/**
 * Returns today's date in the time zone specified in the system property or environment variable `TIME_ZONE`.
 * If the variable is not specified, the system default time zone is used.
 */
fun today(): LocalDate = now().toLocalDate()

/**
 * Returns yesterday's date in the time zone specified in the system property or environment variable `TIME_ZONE`.
 * If the variable is not specified, the system default time zone is used.
 */
fun yesterday(): LocalDate = today().minusDays(1)

/**
 * Returns tomorrow's date in the time zone specified in the system property or environment variable `TIME_ZONE`.
 * If the variable is not specified, the system default time zone is used.
 */
fun tomorrow(): LocalDate = today().plusDays(1)

/**
 * Returns the current date and time in the specified time zone.
 *
 * @param zoneId the time zone
 */
fun nowZoned(zoneId: ZoneId = timeZone): ZonedDateTime = now().atZone(zoneId)

/**
 * Returns the date and time from the source time zone ([zoneId]) in another time zone ([targetZoneId]).
 */
fun LocalDateTime.atZone(zoneId: ZoneId, targetZoneId: ZoneId = timeZone): LocalDateTime {
    return atZone(zoneId).withZoneSameInstant(targetZoneId).toLocalDateTime()
}

/** Returns the first day of the year for the specified date */
fun LocalDate.firstDayOfYear(): LocalDate = withMonth(Month.JANUARY.value).firstDayOfMonth()

/** Returns the first day of the month for the specified date */
fun LocalDate.firstDayOfMonth(): LocalDate = withDayOfMonth(1)

/** Returns the last day of the year for the specified date */
fun LocalDate.lastDayOfYear(): LocalDate = withMonth(Month.DECEMBER.value).lastDayOfMonth()

/** Returns the last day of the month for the specified date */
fun LocalDate.lastDayOfMonth(): LocalDate = withDayOfMonth(month.length(Year.of(year).isLeap))

/** Returns the end of the current day for the specified date */
fun LocalDate.atEndOfDay(): LocalDateTime = atTime(LocalTime.MAX)

/**
 * Converts [LocalDate] to a [Date] object (start of day) in the specified time zone.
 *
 * @param zone time zone for conversion
 */
fun LocalDate.toDate(zone: ZoneId = ZoneId.systemDefault()): Date {
    return Date.from(this.atStartOfDay().atZone(zone).toInstant())
}

/** Returns the start of the day for the specified date and time (00:00) */
fun LocalDateTime.startOfDay(): LocalDateTime = with(LocalTime.MIN)

/** Returns the end of the day for the specified date and time (23:59:59.999999999) */
fun LocalDateTime.endOfDay(): LocalDateTime = with(LocalTime.MAX)

/**
 * Converts [LocalDateTime] to a [Date] object in the specified time zone.
 *
 * @param zone time zone for conversion
 */
fun LocalDateTime.toDate(zone: ZoneId = ZoneId.systemDefault()): Date {
    return Date.from(this.atZone(zone).toInstant())
}

/**
 * Checks if the given time is within the specified time interval.
 *
 * @param start start of the time interval
 * @param end end of the time interval
 */
fun LocalTime.inPeriod(start: LocalTime, end: LocalTime): Boolean {
    return when {
        start == end -> true
        start < end -> this in start..end
        start > end -> this >= start || this <= end
        else -> false
    }
}
