package ru.raysmith.utils

@Deprecated("Use singleOrNull() instead. Will be removed in 5.0.0", ReplaceWith("singleOrNull()"))
fun <T> Iterable<T>.onlyOneOrNull() = if (count() == 1) first() else null

@Deprecated("Use singleOrNull() instead. Will be removed in 5.0.0", ReplaceWith("singleOrNull()"))
fun <T> Iterable<T>.ifOnlyOneOrNull(predicate: (T) -> Boolean): T? {
    val first = firstOrNull() ?: return null
    return if (count() == 1 && predicate(first)) first else null
}

/**
 * Performs the given [let] function on each element of the [Iterable],
 * and then performs the given [action] function on the result of the [let] function.
 */
inline fun <T, R> Iterable<T>.forEachLet(let: (T) -> R, action: (R) -> Unit) {
    for (element in this) action(element.let(let))
}

/** Returns a new map containing all key-value pairs from the original map where the keys are not null. */
@Suppress("UNCHECKED_CAST")
fun <K, V> Map<out K?, V>.filterKeysNotNull(): Map<K, V> = filter { it.key != null } as Map<K, V>
