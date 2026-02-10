package ru.raysmith.utils

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.ExperimentalExtendedContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * Formats the string representation of a number
 * @param digits the number of digits after the decimal point. Should be 0 or more.
 * */
expect fun Double.format(digits: Int): String

/**
 * Rounds a double number
 * @param digits the number of digits after the decimal point. Should be 0 or more.
 * */
expect fun Double.round(digits: Int): Double

/** Returns the value or null if the condition is not met */
inline fun <reified T> T?.orNullIf(predicate: (it: T) -> Boolean): T? {
    return if (predicate(this as T)) null else this
}

/** Returns the value if the string is not empty, otherwise [defaultValue] */
inline fun <T : CharSequence> T.ifNotEmpty(defaultValue: (T) -> T): T =
    if (isNotEmpty()) defaultValue(this) else this


@OptIn(ExperimentalContracts::class, ExperimentalExtendedContracts::class)
/** Returns the result of [block] if [expression] is true, otherwise returns the receiver */
inline fun <T : R, R> T.letIf(expression: Boolean, block: (T) -> R): R {
    contract {
        callsInPlace(block, InvocationKind.AT_MOST_ONCE)
        returns() implies expression
        expression holdsIn block
    }

    return if (expression) block(this) else this
}

@OptIn(ExperimentalContracts::class)
/** Returns the result of [block] if the result of [expression] is true, otherwise returns the receiver */
inline fun <T : R, R> T.letIf(expression: (it: T) -> Boolean, block: (T) -> R): R {
    contract {
        callsInPlace(block, InvocationKind.AT_MOST_ONCE)
    }

    return if (expression(this)) block(this) else this
}

@OptIn(ExperimentalContracts::class, ExperimentalExtendedContracts::class)
/**
 * Performs the given [block] if [expression] is true
 *
 * @return the receiver object
 * */
inline fun <T> T.alsoIf(expression: Boolean, block: (T) -> Unit): T {
    contract {
        callsInPlace(block, InvocationKind.AT_MOST_ONCE)
        returns() implies expression
        expression holdsIn block
    }
    if (expression) block(this)
    return this
}

@OptIn(ExperimentalContracts::class)
/**
 * Performs the given [block] if the result of [expression] is true
 *
 * @return the receiver object
 * */
inline fun <T> T.alsoIf(expression: (it: T) -> Boolean, block: (T) -> Unit): T {
    contract {
        callsInPlace(block, InvocationKind.AT_MOST_ONCE)
    }
    if (expression(this)) block(this)
    return this
}

@OptIn(ExperimentalContracts::class, ExperimentalExtendedContracts::class)
/**
 * Performs the given [block] if [expression] is true
 *
 * @return the receiver object
 * */
inline fun <T> T.applyIf(expression: Boolean, block: T.() -> Unit): T {
    contract {
        callsInPlace(block, InvocationKind.AT_MOST_ONCE)
        returns() implies expression
        expression holdsIn block
    }
    if (expression) block()
    return this
}

@OptIn(ExperimentalContracts::class)
/**
 * Performs the given [block] if the result of [expression] is true
 *
 * @return the receiver object
 * */
inline fun <T> T.applyIf(expression: (it: T) -> Boolean, block: T.() -> Unit): T {
    contract {
        callsInPlace(block, InvocationKind.AT_MOST_ONCE)
    }
    if (expression(this)) block()
    return this
}

/** Returns the value corresponding to the outcome */
fun <T> Boolean.outcome(whenTrue: T, whenFalse: T): T {
    return if (this) whenTrue else whenFalse
}

/** Returns the value corresponding to the outcome */
fun <T> Boolean.outcome(whenTrue: () -> T, whenFalse: () -> T): T {
    return if (this) whenTrue() else whenFalse()
}
