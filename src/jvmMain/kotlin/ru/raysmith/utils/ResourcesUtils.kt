package ru.raysmith.utils

import java.io.File
import java.io.InputStream

@Suppress("NOTHING_TO_INLINE")
inline fun String.asResourceStream(): InputStream {
    return ClassLoader.getSystemClassLoader().getResourceAsStream(this)
        ?: error("resource file '$this' not found")
}

@Suppress("NOTHING_TO_INLINE")
inline fun String.asResource(): File {
    return ClassLoader.getSystemClassLoader().getResource(this)?.let {
        File(it.toURI())
    } ?: error("resource file '$this' not found")
}