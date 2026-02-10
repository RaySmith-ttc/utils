package ru.raysmith.utils

import java.io.File
import java.io.InputStream

/**
 * Returns an [InputStream] for the resource file with the given name.
 * The resource file should be located in the classpath.
 * If the resource file is not found, an [IllegalStateException] is thrown.
 * */
@Suppress("NOTHING_TO_INLINE")
inline fun String.asResourceStream(): InputStream {
    return ClassLoader.getSystemClassLoader().getResourceAsStream(this)
        ?: error("resource file '$this' not found")
}

/**
 * Returns a [File] object for the resource file with the given name.
 * The resource file should be located in the classpath.
 * If the resource file is not found, an [IllegalStateException] is thrown.
 *
 * **Note**: This method does not work for files located inside JAR files.
 * In such cases, use [asResourceStream] instead.
 * */
@Suppress("NOTHING_TO_INLINE")
@Deprecated("Use asResourceStream() instead. This method does not work for files located inside JAR files. Will be removed in 5.0.0.", ReplaceWith("this.asResourceStream()"))
inline fun String.asResource(): File {
    return ClassLoader.getSystemClassLoader().getResource(this)?.let {
        if (it.path.contains(".jar!/")) throw IllegalStateException("File can not be obtained from jar file. Use \"$this\".asResourceStream() instead")
        File(it.toURI())
    } ?: error("resource file '$this' not found")
}
