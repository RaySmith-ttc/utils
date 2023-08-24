package ru.raysmith.utils

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * Форматирует строковое представление числа
 * @param digits количество символов после запятой
 * */
expect fun Double.format(digits: Int): String


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
        returns() implies expression
    }
    return if (expression) block(this) else this
}

@OptIn(ExperimentalContracts::class)
inline fun <T> T.letIf(expression: (it: T) -> Boolean, block: (T) -> T): T {
    kotlin.contracts.contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }
    return if (expression(this)) block(this) else this
}

@OptIn(ExperimentalContracts::class)
inline fun <T> T.alsoIf(expression: Boolean, block: (T) -> Unit): T {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
        returns() implies expression
    }
    if (expression) block(this)
    return this
}

@OptIn(ExperimentalContracts::class)
inline fun <T> T.alsoIf(expression: (it: T) -> Boolean, block: (T) -> Unit): T {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }
    if (expression(this)) block(this)
    return this
}

@OptIn(ExperimentalContracts::class)
inline fun <T> T.applyIf(expression: Boolean, block: T.() -> Unit): T {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
        returns() implies expression
    }
    if (expression) block()
    return this
}

@OptIn(ExperimentalContracts::class)
inline fun <T> T.applyIf(expression: (it: T) -> Boolean, block: T.() -> Unit): T {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }
    if (expression(this)) block()
    return this
}

/** Returns the value corresponding to the outcome */
fun <T> Boolean.outcome(whenTrue: T, whenFalse: T): T {
    return if (this) whenTrue else whenFalse
}

/**
 * Returns a string containing the first [n] characters from this string + '...',
 * or the entire string if this string is shorter.
 *
 * @throws IllegalArgumentException if [n] is negative.
 * */
fun String.takeOrCut(n: Int) = letIf(length > n) {
    "${it.take(n)}..."
}