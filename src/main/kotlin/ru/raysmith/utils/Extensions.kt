package ru.raysmith.utils

import java.net.URL
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

/**
 * Форматирует строковое представление счисла
 * @param digits количество символов после запятой
 * */
fun Double.format(digits: Int): String {
    require(digits >= 0) { "digits must be 0 or more" }
    return "%.${digits}f".format(this)
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

/**
 * Ищет запрошенный параметр запроса
 *
 * @param param параметр
 * @return значение параметра или null
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