package ru.raysmith.utils

import java.math.BigDecimal

val BigDecimal.isZero: Boolean
    get() = this.compareTo(BigDecimal.ZERO) == 0

fun BigDecimal?.eq(other: BigDecimal?): Boolean =
    if (this != null && other != null) this.compareTo(other) == 0 else this == other

fun BigDecimal?.neq(other: BigDecimal?): Boolean = !this.eq(other)
