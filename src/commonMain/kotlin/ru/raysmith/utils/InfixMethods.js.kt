package ru.raysmith.utils

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

/**
 * Returns true if both operands is not null. Don't use a boolean as the left operand!
 * */
@OptIn(ExperimentalContracts::class)
infix fun Any?.notNull(x: Any?): Boolean {
    contract {
        returns(true) implies (this@notNull != null && x != null)
    }
    return if (this is Boolean) this && x != null
    else this != null && x != null
}

/**
 * Returns true if one of operands is not null. Don't use a boolean as the left operand!
 * */
infix fun Any?.orNotNull(x: Any?): Boolean {
    return if (this is Boolean) this || x != null
    else this != null || x != null
}

/**
 * Returns true if one of operands is null. Don't use a boolean as the left operand!
 * */
infix fun Any?.orNull(x: Any?): Boolean {
    return if (this is Boolean) this || x == null
    else this == null || x == null
}

/**
 * Returns true if both operands is null. Don't use a boolean as the left operand!
 * */
@OptIn(ExperimentalContracts::class)
infix fun Any?.bothNull(x: Any?): Boolean {
    contract {
        returns(true) implies (this@bothNull == null && x == null)
    }
    return if (this is Boolean) this && x == null
    else this == null && x == null
}