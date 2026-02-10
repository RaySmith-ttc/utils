package ru.raysmith.utils

/**
 * Generates a UUID string.
 *
 * @param zero If true, generates a UUID consisting of zeros. Default is false.
 * @return A UUID string.
 */
expect fun uuid(zero: Boolean = false): String
