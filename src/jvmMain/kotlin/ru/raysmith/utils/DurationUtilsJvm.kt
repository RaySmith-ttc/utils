package ru.raysmith.utils

import java.time.DateTimeException
import java.time.LocalTime
import java.time.temporal.ChronoField
import java.time.temporal.ChronoUnit
import java.time.temporal.Temporal
import kotlin.time.Duration
import kotlin.time.toKotlinDuration

/**
 * Obtains a kotlin [Duration] representing the duration between two temporal objects.
 *
 * This calculates the duration between two temporal objects. If the objects are of different types, then the duration
 * is calculated based on the type of the first object. For example, if the first argument is a [LocalTime] then the
 * second argument is converted to a [LocalTime].
 *
 * The specified temporal objects must support the [SECONDS][ChronoUnit.SECONDS] unit. For full accuracy, either the
 * [NANOS][ChronoUnit.NANOS] unit or the [NANO_OF_SECOND][ChronoField.NANO_OF_SECOND] field should be supported.
 *
 * The result of this method can be a negative period if the end is before the start. To guarantee to obtain a positive
 * duration use [absoluteValue][Duration.absoluteValue] on the result.
 *
 * @param startInclusive  the start instant, inclusive, not null
 * @param endExclusive  the end instant, exclusive, not null
 * @return a [Duration], not null
 * @throws DateTimeException if the seconds between the temporals cannot be obtained
 * @throws ArithmeticException if the calculation exceeds the capacity of {@code Duration}
 */
fun Duration.Companion.between(startInclusive: Temporal, endExclusive: Temporal) =
    java.time.Duration.between(startInclusive, endExclusive).toKotlinDuration()
