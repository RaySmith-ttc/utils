package ru.raysmith.utils

import kotlin.random.Random

private val CHARS = ('0'..'9') + ('a'..'f')

actual fun uuid() = Random.Default.nextBytes(16).apply {
    this[6] = ((this[6].toInt() and 0x0F) or (4 shl 4)).toByte()
    this[8] = ((this[8].toInt() and 0x3F) or 0x80).toByte()
}.let {
    CharArray(36).apply {
        var charIndex = 0
        for (range in listOf(0 until 4, 4 until 6, 6 until 8, 8 until 10, 10 until 16)) {
            for (i in range) {
                val octetPair = it[i]
                set(charIndex++, CHARS[octetPair.toInt().shr(4) and 0b00001111])
                set(charIndex++, CHARS[octetPair.toInt() and 0b00001111])
            }
            if (charIndex < 36) {
                set(charIndex++, '-')
            }
        }
    }.concatToString()
}