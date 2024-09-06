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
        if (it.path.contains(".jar!/")) throw IllegalStateException("File can not be obtained from jar file. Use \"$this\".asResourceStream() instead")
        File(it.toURI())
    } ?: error("resource file '$this' not found")
}