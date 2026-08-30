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

/**
 * If [other] is not in the collection, returns a new collection with [other] added.
 * If [other] is in the collection, returns a new collection with [other] removed
 * */
operator fun <T> MutableCollection<T>.divAssign(other: T) {
    if (contains(other)) {
        this.remove(other)
    } else {
        this.add(other)
    }
}

/**
 * If [other] is not in the set, returns a new set with [other] added.
 * If [other] is in the set, returns a new set with [other] removed
 * */
operator fun <T> Set<T>.div(other: T): Set<T> {
    return if (contains(other)) {
        this.minus(other)
    } else {
        this.plus(other)
    }
}

/**
 * If [other] is not in the iterable, returns a new list with [other] added.
 * If [other] is in the iterable, returns a new list with [other] removed
 * */
operator fun <T, I : Iterable<T>> I.div(other: T): List<T> {
    return if (contains(other)) {
        this.minus(other)
    } else {
        this.plus(other)
    }
}

/**
 * Returns `true` if all elements match the given [predicate] with their index.
 *
 * Note that if the collection contains no elements, [allIndexed] returns `true`
 * because there are no elements to violate the condition.
 */
inline fun <T> Iterable<T>.allIndexed(predicate: (index: Int, item: T) -> Boolean): Boolean {
    if (this is Collection && isEmpty()) return true
    forEachIndexed { index, item ->
        if (!predicate(index, item)) return false
    }
    return true
}

/**
 * Returns `true` if at least one element matches the given [predicate] with its index.
 * Returns `false` if the collection is empty.
 */
inline fun <T> Iterable<T>.anyIndexed(predicate: (index: Int, item: T) -> Boolean): Boolean {
    if (this is Collection && isEmpty()) return false
    forEachIndexed { index, item ->
        if (predicate(index, item)) return true
    }
    return false
}
