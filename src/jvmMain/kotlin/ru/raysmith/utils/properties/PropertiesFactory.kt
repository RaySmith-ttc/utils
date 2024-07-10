package ru.raysmith.utils.properties

import java.io.File
import java.io.FileNotFoundException
import java.nio.file.Path
import java.util.*

/** Contains factory methods for getting properties */
object PropertiesFactory {

    /**
     * Return properties from resource file [path]
     *
     * @throws FileNotFoundException if file not found
     * */
    @Suppress("NOTHING_TO_INLINE")
    inline fun from(path: String) = Properties().apply {
        ClassLoader.getSystemClassLoader().getResourceAsStream(path)?.use { load(it) }
            ?: throw FileNotFoundException("Property file $path not found")
    }

    /** Return properties from resource file [path] or null if file not exist */
    @Suppress("NOTHING_TO_INLINE")
    inline fun fromOrNull(path: String) = ClassLoader.getSystemClassLoader().getResourceAsStream(path)?.let {
        Properties().apply { load(it) }
    }

    /** Return properties for [path] */
    fun from(path: Path): Properties = from(path.toFile())

    /** Return properties for [file] */
    fun from(file: File) = Properties().apply {
        file.inputStream().use { load(it) }
    }
}