package ru.raysmith.utils

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * Executes the given [block] and returns its result, or `null` if an exception was thrown.
 * The exception is caught and ignored.
 * */
@OptIn(ExperimentalContracts::class)
inline fun <T> safe(block: () -> T): T? {
    contract {
        callsInPlace(block, InvocationKind.AT_MOST_ONCE)
    }

    return try {
        block()
    } catch (_: Exception) {
        null
    }
}
