package ru.raysmith.utils

fun <T> Iterable<T>.onlyOneOrNull() = if (count() == 1) first() else null
fun <T> Iterable<T>.ifOnlyOneOrNull(predicate: (T) -> Boolean): T? {
    val first = firstOrNull() ?: return null
    return if (count() == 1 && predicate(first)) first else null
}