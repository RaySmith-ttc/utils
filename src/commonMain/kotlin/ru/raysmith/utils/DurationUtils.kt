package ru.raysmith.utils

import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

val Duration.ms get() = inWholeMilliseconds
val Long.ms get() = milliseconds
val Int.ms get() = milliseconds