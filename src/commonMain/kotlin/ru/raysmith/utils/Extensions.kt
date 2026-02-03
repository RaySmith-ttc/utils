package ru.raysmith.utils

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * Форматирует строковое представление числа
 * @param digits количество символов после запятой
 * */
expect fun Double.format(digits: Int): String

/**
 * Округляет число double
 * @param digits количество символов после запятой
 * */
expect fun Double.round(digits: Int): Double


/** Возвращает значение или null если условие не выполнено */
inline fun <reified T> T?.orNullIf(predicate: (it: T) -> Boolean): T? {
    return if (predicate(this as T)) null else this
}

/** Возвращает значение если строка не пустая, иначе [defaultValue] */
inline fun <T : CharSequence> T.ifNotEmpty(defaultValue: (T) -> T): T =
    if (isNotEmpty()) defaultValue(this) else this

@OptIn(ExperimentalContracts::class)
inline fun <T : R, R> T.letIf(expression: Boolean, block: (T) -> R): R {
    contract {
        callsInPlace(block, InvocationKind.AT_MOST_ONCE)
        returns() implies expression
    }
    return if (expression) block(this) else this
}

@OptIn(ExperimentalContracts::class)
inline fun <T : R, R> T.letIf(expression: (it: T) -> Boolean, block: (T) -> R): R {
    contract {
        callsInPlace(block, InvocationKind.AT_MOST_ONCE)
    }
    return if (expression(this)) block(this) else this
}

@OptIn(ExperimentalContracts::class)
inline fun <T> T.alsoIf(expression: Boolean, block: (T) -> Unit): T {
    contract {
        callsInPlace(block, InvocationKind.AT_MOST_ONCE)
        returns() implies expression
    }
    if (expression) block(this)
    return this
}

@OptIn(ExperimentalContracts::class)
inline fun <T> T.alsoIf(expression: (it: T) -> Boolean, block: (T) -> Unit): T {
    contract {
        callsInPlace(block, InvocationKind.AT_MOST_ONCE)
    }
    if (expression(this)) block(this)
    return this
}

@OptIn(ExperimentalContracts::class)
inline fun <T> T.applyIf(expression: Boolean, block: T.() -> Unit): T {
    contract {
        callsInPlace(block, InvocationKind.AT_MOST_ONCE)
        returns() implies expression
    }
    if (expression) block()
    return this
}

@OptIn(ExperimentalContracts::class)
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

/**
 * Returns a string containing the first [n] characters from this string + 3-bytes character `…`,
 * or the entire string if the string is shorter.
 *
 * Example:
 * ```
 * "Hello world".takeOrCut(6) // "Hello…"
 * "Hello world".takeOrCut(5, countDots = false) // "Hello…"
 * ```
 *
 * @param countDots if true and string longer than [n], result string's length with included `…` will be [n] length,
 * otherwise [n] length + 1
 * @throws IllegalArgumentException if [n] is negative.
 * */
fun String.takeOrCut(n: Int, countDots: Boolean = true) = when {
    n == 0 && countDots -> ""
    n == 0 -> "…"
    else -> letIf(length > n) {
        "${it.take(if (countDots) n - 1 else n)}…"
    }
}