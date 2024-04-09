package ru.raysmith.utils

import java.time.*
import java.util.*

// TODO add tests

private val timeZone = (System.getProperty("TIME_ZONE") ?: System.getenv("TIME_ZONE"))?.let { ZoneId.of(it) }
    ?: ZoneId.systemDefault()

/**
 * Возвращает текущее время и дату в часовом поясе, указанном в системной переменной или переменной окружения TIME_ZONE.
 * Если переменная не указана используется системный часовой пояс.
 */
fun now(): LocalDateTime = LocalDateTime.now(timeZone)

/**
 * Возвращает текущее время в часовом поясе, указанном в системной переменной или переменной окружения TIME_ZONE.
 * Если переменная не указана используется системный часовой пояс.
 */
fun nowTime(): LocalTime = now().toLocalTime()

/**
 * Возвращает сегодняшнюю дату в часовом поясе, указанном в системной переменной или переменной окружения TIME_ZONE.
 * Если переменная не указана используется системный часовой пояс.
 */
fun today(): LocalDate = now().toLocalDate()

/**
 * Возвращает вчерашнюю дату в часовом поясе, указанном в системной переменной или переменной окружения TIME_ZONE.
 * Если переменная не указана используется системный часовой пояс.
 */
fun yesterday(): LocalDate = today().minusDays(1)

/**
 * Возвращает завтрашнюю дату в часовом поясе, указанном в системной переменной или переменной окружения TIME_ZONE.
 * Если переменная не указана используется системный часовой пояс.
 */
fun tomorrow(): LocalDate = today().plusDays(1)

/**
 * Возвращает текущее время и дату в указанном часовом поясе.
 *
 * @param zoneId часовой пояс
 */
fun nowZoned(zoneId: ZoneId = timeZone): ZonedDateTime = now().atZone(zoneId)

/** Возвращает первый день года для указанной даты */
fun LocalDate.firstDayOfYear(): LocalDate = withMonth(Month.JANUARY.value).firstDayOfMonth()

/** Возвращает первый день месяца для указанной даты */
fun LocalDate.firstDayOfMonth(): LocalDate = withDayOfMonth(1)

/** Возвращает последний день года для указанной даты */
fun LocalDate.lastDayOfYear(): LocalDate = withMonth(Month.DECEMBER.value).lastDayOfMonth()

/** Возвращает последний день месяца для указанной даты */
fun LocalDate.lastDayOfMonth(): LocalDate = withDayOfMonth(month.length(Year.of(year).isLeap))

/** Возвращает начало дня для указанной даты и времени */
fun LocalDateTime.startOfDay(): LocalDateTime = with(LocalTime.MIN)

/** Возвращает конец дня для указанной даты и времени */
fun LocalDateTime.endOfDay(): LocalDateTime = with(LocalTime.MAX)

/** Возвращает конец текущего дня для указанной даты */
fun LocalDate.atEndOfDay(): LocalDateTime = atTime(LocalTime.MAX)

/**
 * Преобразует [LocalDateTime] в объект [Date] в указанном часовом поясе.
 *
 * @param zone часовой пояс для преобразования
 */
fun LocalDateTime.toDate(zone: ZoneId = ZoneId.systemDefault()): Date {
    return Date.from(this.atZone(zone).toInstant())
}

/**
 * Преобразует [LocalDate] в объект [Date], начало дня, в указанном часовом поясе.
 *
 * @param zone часовой пояс для преобразования
 */
fun LocalDate.toDate(zone: ZoneId = ZoneId.systemDefault()): Date {
    return Date.from(this.atStartOfDay().atZone(zone).toInstant())
}

/**
 * Проверяет, находится ли данное время в заданном временном интервале.
 *
 * @param start начало временного интервала
 * @param end конец временного интервала
 */
fun LocalTime.inPeriod(start: LocalTime, end: LocalTime): Boolean {
    return when {
        start == end -> true
        start < end -> this in start..end
        start > end -> this >= start || this <= end
        else -> false
    }
}