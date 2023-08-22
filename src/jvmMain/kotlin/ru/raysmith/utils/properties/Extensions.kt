package ru.raysmith.utils.properties

import java.util.*

fun Properties.getOrNull(key: String): String? = getProperty(key, null)