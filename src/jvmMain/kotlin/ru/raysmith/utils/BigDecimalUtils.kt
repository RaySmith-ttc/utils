package ru.raysmith.utils

import java.math.BigDecimal

/** Returns `true` if the [BigDecimal] value is equal to zero, ignoring the scale. */
val BigDecimal.isZero: Boolean
    get() = this.compareTo(BigDecimal.ZERO) == 0

/**
 * Returns `true` if this [BigDecimal] is equal to [other], ignoring the scale.
 * If both values are `null`, returns `true`. If only one value is `null`, returns `false`.
 */
fun BigDecimal?.eq(other: BigDecimal?): Boolean =
    if (this != null && other != null) this.compareTo(other) == 0 else this == other

/**
 * Returns `true` if this [BigDecimal] is not equal to [other], ignoring the scale.
 * If both values are `null`, returns `false`. If only one value is `null`, returns `true`.
 */
fun BigDecimal?.neq(other: BigDecimal?): Boolean = !this.eq(other)

/**
 * Returns `true` if this [Iterable] of [BigDecimal] has the same elements in the same order as [other],
 * comparing [BigDecimal] elements using [eq] (ignoring scale).
 * If both iterables are `null`, returns `true`. If only one iterable is `null`, returns `false`.
 */
fun Iterable<BigDecimal>?.eq(other: Iterable<BigDecimal>?): Boolean =
    if (this != null && other != null && this.count() == other.count()) {
        val thisIterator = this.iterator()
        val otherIterator = other.iterator()

        while (thisIterator.hasNext() && otherIterator.hasNext()) {
            if (thisIterator.next().neq(otherIterator.next())) return false
        }

        true
    } else this == other

/**
 * Returns `true` if this [Iterable] of [BigDecimal] is not equal to [other] according to [eq].
 * If both iterables are `null`, returns `false`. If only one iterable is `null`, returns `true`.
 */
fun Iterable<BigDecimal>?.neq(other: Iterable<BigDecimal>?): Boolean = !this.eq(other)
