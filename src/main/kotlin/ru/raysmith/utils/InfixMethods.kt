package ru.raysmith.utils

/**
 * Returns true if both operands is not null. Don't use a boolean as the left operand!
 * */
infix fun Any?.notNull(x: Any?): Boolean {
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
infix fun Any?.bothNull(x: Any?): Boolean {
    return if (this is Boolean) this && x == null
    else this == null && x == null
}