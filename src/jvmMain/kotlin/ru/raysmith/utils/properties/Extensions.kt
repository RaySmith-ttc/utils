package ru.raysmith.utils.properties

import java.util.*

/** Alias of the [Properties.getProperty] method with default value of `null` */
fun Properties.getOrNull(key: String): String? = getProperty(key, null)
