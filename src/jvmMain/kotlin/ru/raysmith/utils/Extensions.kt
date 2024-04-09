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
}

actual fun Double.round(digits: Int): Double {
    require(digits >= 0) { "digits must be positive" }
    if (digits == 0) return roundToInt().toDouble()

    val p = 10.0.pow(digits)
    return (Math.round(this * p) / p)
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



/**
 * Ищет запрошенный параметр запроса
 *
 * @param param параметр
 * @return значение параметра или null, если параметр не найден
 * */
fun URL.getParam(param: String): String? {
    val queryParams = this.query ?: return null
    val pairs = queryParams.split("&")
    for (pair in pairs) {
        val (key, value) = pair.split("=")
        if (key == param) {
            return value
        }
    }
    return null
}