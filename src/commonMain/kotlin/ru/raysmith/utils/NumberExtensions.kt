package ru.raysmith.utils

import kotlin.math.log10
import kotlin.math.pow
import kotlin.math.roundToInt

/**
 * Converts a number of bytes to a human-readable string format (e.g., "1 kb", "2 mb").
 *
 * @receiver The number of bytes to convert.
 * @return A string representing the size in a human-readable format.
 */
fun Number.bytesToString(): String {
    with(toLong()) {
        if (this <= 0) {
            return "0 b"
        }

        val units = arrayOf("b", "kb", "mb", "gb", "tb", "pb")
        val digitGroups = (log10(this.toDouble()) / log10(1024.0)).toInt().coerceAtMost(units.lastIndex)

        return "${(this / 1024.0.pow(digitGroups.toDouble())).roundToInt()} ${units[digitGroups]}"
    }
}
