package ru.raysmith.utils

/**
 * Возвращает true если оба операнда не null
 * */
infix fun Any?.notNull(x: Any?): Boolean {
    return if (this is Boolean) this && x != null
    else this != null && x != null
}

/**
 * Возвращает true если один из операндов не null
 * */
infix fun Any?.orNotNull(x: Any?): Boolean {
    return if (this is Boolean) this || x != null
    else this != null || x != null
}

/**
 * Возвращает true если один из операндов null
 * */
infix fun Any?.orNull(x: Any?): Boolean {
    return if (this is Boolean) this || x == null
    else this == null || x == null
}

/**
 * Возвращает true если оба операнда null
 * */
infix fun Any?.bothNull(x: Any?): Boolean {
    return if (this is Boolean) this && x == null
    else this == null && x == null
}