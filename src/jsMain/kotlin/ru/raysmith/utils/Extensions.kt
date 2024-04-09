package ru.raysmith.utils

actual fun Double.format(digits: Int): String {
    require(digits >= 0) { "digits must be 0 or more" }
    return asDynamic().toFixed(digits).unsafeCast<String>()
}

actual fun Double.round(digits: Int): Double {
    require(digits >= 0) { "digits must be positive" }
    return asDynamic().toFixed(digits).unsafeCast<String>().toDouble()
}