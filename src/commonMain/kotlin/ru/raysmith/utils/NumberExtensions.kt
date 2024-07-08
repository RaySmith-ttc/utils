package ru.raysmith.utils

import kotlin.math.log10
import kotlin.math.pow
import kotlin.math.roundToInt


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