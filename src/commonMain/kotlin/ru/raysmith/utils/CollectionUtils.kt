package ru.raysmith.utils

fun <T> Iterable<T>.onlyOneOrNull() = if (count() == 1) first() else null
fun <T> Iterable<T>.ifOnlyOneOrNull(predicate: (T) -> Boolean): T? {
    val first = firstOrNull() ?: return null
    return if (count() == 1 && predicate(first)) first else null
}

inline fun <T, R> Iterable<T>.forEachLet(let: (T) -> R, action: (R) -> Unit) {
    for (element in this) action(element.let(let))
}