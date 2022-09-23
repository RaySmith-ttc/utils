package ru.raysmith.utils

import java.net.URL
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.util.*
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * Форматирует строковое представление числа
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

/** Возвращает значение или null если условие не выполнено */
inline fun <reified T> T?.orNullIf(predicate: (it: T) -> Boolean): T? {
    return if (predicate(this as T)) null else this
}

/** Возвращает значение если строка не пустая, иначе [defaultValue] */
inline fun <T> T.ifNotEmpty(defaultValue: (T) -> T): T where T : CharSequence =
    if (isNotEmpty()) defaultValue(this) else this

@OptIn(ExperimentalContracts::class)
inline fun <T> T.letIf(expression: Boolean, block: (T) -> T): T {
    kotlin.contracts.contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }
    return if (expression) block(this) else this
}

@OptIn(ExperimentalContracts::class)
inline fun <T> T.alsoIf(expression: Boolean, block: (T) -> Unit): T {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }
    if (expression) block(this)
    return this
}

@OptIn(ExperimentalContracts::class)
inline fun <T> T.applyIf(expression: Boolean, block: T.() -> Unit): T {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }
    if (expression) block()
    return this
}

/** Returns the value corresponding to the outcome */
inline fun <reified T> Boolean.outcome(whenTrue: T, whenFalse: T): T {
    return if (this) whenTrue else whenFalse
}

/**
 * Returns a string containing the first [n] characters from this string + '...',
 * or the entire string if this string is shorter.
 *
 * @throws IllegalArgumentException if [n] is negative.
 * */
fun String.takeOrCut(n: Int) = this.letIf(this.length > n) {
    "${it.take(n)}..."
}