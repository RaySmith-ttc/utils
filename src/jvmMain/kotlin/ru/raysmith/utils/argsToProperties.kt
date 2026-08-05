package ru.raysmith.utils

/**
 * Sets system properties from jvm args
 *
 * Usage:
 * ```
 * fun main(args: Array<String>) {
 *     argsToProperties(args)
 * }
 * ```
 * */
fun argsToProperties(args: Array<String>) {
    args.forEach {
        if (it.contains("=")) {
            val (key, value) = it.split("=")
            System.setProperty(key, value)
        }
    }
}
