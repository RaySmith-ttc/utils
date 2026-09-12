package ru.raysmith.utils

import java.math.BigDecimal

open class BigDecimalProgression internal constructor(
    start: BigDecimal,
    endInclusive: BigDecimal,
    step: BigDecimal
) : Iterable<BigDecimal> {
    init {
        require(!step.isZero) { "Step must be non-zero." }
        require(setOf(start.scale(), endInclusive.scale(), step.scale()).size == 1) {
            "All of the numbers in a progression must have the same scale"
        }
    }

    private fun mod(a: BigDecimal, b: BigDecimal): BigDecimal {
        val mod = a % b
        return if (mod >= BigDecimal.ZERO) mod else mod + b
    }

    private fun differenceModulo(a: BigDecimal, b: BigDecimal, c: BigDecimal): BigDecimal {
        return mod(mod(a, c) - mod(b, c), c)
    }

    internal fun getProgressionLastElement(start: BigDecimal, end: BigDecimal, step: BigDecimal): BigDecimal = when {
        step > BigDecimal.ZERO -> if (start >= end) end else end - differenceModulo(end, start, step)
        step < BigDecimal.ZERO -> if (start <= end) end else end + differenceModulo(start, end, -step)
        else -> throw IllegalArgumentException("Step is zero.")
    }

    /**
     * The first element in the progression.
     */
    val first: BigDecimal = start

    /**
     * The last element in the progression.
     */
    val last: BigDecimal = getProgressionLastElement(start, endInclusive, step)

    /**
     * The step of the progression.
     */
    val step: BigDecimal = step

    private val p = Math.pow(10.0, step.scale().toDouble()).toBigDecimal()

    override fun iterator(): BigDecimalIterator = BigDecimalProgressionIterator(first, last, step)

    /**
     * Checks if the progression is empty.
     *
     * Progression with a positive step is empty if its first element is greater than the last element.
     * Progression with a negative step is empty if its first element is less than the last element.
     */
    open fun isEmpty(): Boolean = if (step > BigDecimal.ZERO) first > last else first < last

    override fun equals(other: Any?): Boolean =
        other is BigDecimalProgression && (isEmpty() && other.isEmpty() ||
                first.eq(other.first) && last.eq(other.last) && step.eq(other.step))

    override fun hashCode(): Int =
        if (isEmpty()) -1 else (31 * (31 * (first * p).toInt() + (last * p).toInt()) + (step * p).toInt())

    override fun toString(): String =
        if (step > BigDecimal.ZERO) "$first..$last step $step" else "$first downTo $last step ${-step}"

    companion object {
        /**
         * Creates [BigDecimalProgression] within the specified bounds of a closed range.
         *
         * The progression starts with the [rangeStart] value and goes toward the [rangeEnd] value not excluding it, with the specified [step].
         * In order to go backwards the [step] must be negative.
         *
         * [step] must not be equal to zero.
         */
        fun fromClosedRange(
            rangeStart: BigDecimal,
            rangeEnd: BigDecimal,
            step: BigDecimal
        ): BigDecimalProgression =
            BigDecimalProgression(rangeStart, rangeEnd, step)
    }
}

infix fun BigDecimal.downTo(to: BigDecimal): BigDecimalProgression {
    return BigDecimalProgression.fromClosedRange(
        this,
        to,
        if (this.scale() == 0) BigDecimal(-1) else BigDecimal("-0.${"0".repeat(this.scale() - 1)}1")
    )
}

internal class BigDecimalProgressionIterator(first: BigDecimal, last: BigDecimal, val step: BigDecimal) :
    BigDecimalIterator() {
    private val finalElement: BigDecimal = last
    private var hasNext: Boolean = if (step > BigDecimal.ZERO) first <= last else first >= last
    private var next: BigDecimal = if (hasNext) first else finalElement

    override fun hasNext(): Boolean = hasNext

    override fun nextBigDecimal(): BigDecimal {
        val value = next
        if (value.eq(finalElement)) {
            if (!hasNext) throw NoSuchElementException()
            hasNext = false
        } else {
            next += step
        }
        return value
    }
}

abstract class BigDecimalIterator : Iterator<BigDecimal> {
    final override fun next(): BigDecimal = nextBigDecimal()

    /**
     * Returns the next element in the iteration without boxing conversion.
     * @throws NoSuchElementException if the iteration has no next element.
     */
    abstract fun nextBigDecimal(): BigDecimal
}

infix fun BigDecimalProgression.step(step: BigDecimal): BigDecimalProgression {
    if (step < BigDecimal.ZERO) throw IllegalArgumentException("Step must be positive, was: $step.")
    return BigDecimalProgression.fromClosedRange(first, last, if (this.step > BigDecimal.ZERO) step else -step)
}

infix fun ClosedRange<BigDecimal>.step(step: BigDecimal): BigDecimalProgression {
    if (step < BigDecimal.ZERO) throw IllegalArgumentException("Step must be positive, was: $step.")
    return BigDecimalProgression.fromClosedRange(start, endInclusive, if (endInclusive > start) step else -step)
}
