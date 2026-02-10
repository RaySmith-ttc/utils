package ru.raysmith.utils

import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

/** Alias of the [Duration.inWholeMilliseconds] property */
val Duration.ms get() = inWholeMilliseconds

/** Alias of the [Duration.milliseconds] property */
val Long.ms get() = milliseconds

/** Alias of the [Duration.milliseconds] property */
val Int.ms get() = milliseconds
