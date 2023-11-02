package ru.raysmith.utils

import java.net.URL
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.util.*
import kotlin.math.pow
import kotlin.math.roundToInt

actual fun Double.format(digits: Int): String {
    require(digits >= 0) { "digits must be 0 or more" }
    if (digits == 0) return roundToInt().toString()

    val p = 10.0.pow(digits)
    return (Math.round(this * p) / p).toString()
//    return "%.${digits}f".format(Locale.ENGLISH, this)
}

fun Date.toLocalDateTime(zone: ZoneId = ZoneId.systemDefault()): LocalDateTime {
    return this.toInstant()
        .atZone(zone)
        .toLocalDateTime()
}

fun Date.toLocalDate(zone: ZoneId = ZoneId.systemDefault()): LocalDate {
    return this.toInstant()
        .atZone(zone)
        .toLocalDate()
}

fun LocalDateTime.toDate(zone: ZoneId = ZoneId.systemDefault()): Date {
    return Date.from(this.atZone(zone).toInstant())
}
fun LocalDate.toDate(zone: ZoneId = ZoneId.systemDefault()): Date {
    return Date.from(this.atStartOfDay().atZone(zone).toInstant())
}

fun LocalTime.inPeriod(start: LocalTime, end: LocalTime): Boolean {
    return when {
        start == end -> true
        start < end -> this in start..end
        start > end -> this >= start || this <= end
        else -> false
    }
}

/**
 * Ищет запрошенный параметр запроса
 *
 * @param param параметр
 * @return значение параметра или null, если параметр не найден
 * */
fun URL.getParam(param: String): String? {
    this.query?.split("&")?.forEach { q ->
        q.split("=").let {
            if (it.first() == param) {
                return if (it.size > 1) it.last() else ""
            }
        }
    }
    return null
}