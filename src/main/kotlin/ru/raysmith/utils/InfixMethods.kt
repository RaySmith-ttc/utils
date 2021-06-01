package ru.raysmith.utils

/**
 * Возвращет true если оба операнда не null
 * */
infix fun Any?.notNull(x: Any?): Boolean {
    return if (this is Boolean) this && x != null
    else this != null && x != null
}

/**
 * Возвращет true если один за операндов не null
 * */
infix fun Any?.orNotNull(x: Any?): Boolean {
    return if (this is Boolean) this || x != null
    else this != null || x != null
}

/**
 * Возвращет true если оба операнда null
 * */
infix fun Any?.bothNull(x: Any?): Boolean {
    return if (this is Boolean) this && x == null
    else this == null && x == null
}