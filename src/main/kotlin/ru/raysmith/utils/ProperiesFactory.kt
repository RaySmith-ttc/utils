package ru.raysmith.utils

import java.io.File
import java.io.FileInputStream
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*

/**
 * Используется для доступа к файлам свойств
 * */
object PropertiesFactory {

    /**
     * Вовзращает [мэнеджер][PropertiesManager] свойств для файла
     *
     * @param file путь к файлу в директории ресурсов
     * */
    fun from(file: String): PropertiesManager {
//        val propertiesPath = javaClass.classLoader.getResource(file).let {
//            require(it != null) { "Properties file '$file' not found" }
//            it.path
//        }
//        val propertiesPath = ClassLoader.getSystemClassLoader().getResourceAsStream(file).let {
//            require(it != null) { "Properties file '$file' not found" }
//            it.path
//        }

        val properties = Properties().apply {
            ClassLoader.getSystemClassLoader().getResourceAsStream(file).use { load(it) }
        }


        return PropertiesManager(properties)
    }

    /**
     * Вовзращает [мэнеджер][PropertiesManager] свойств для файла
     *
     * @param path путь к файлу
     * */
    fun from(path: Path): PropertiesManager = from(path.toFile())

    /**
     * Вовзращает [мэнеджер][PropertiesManager] свойств для файла
     *
     * @param file файл
     * */
    fun from(file: File): PropertiesManager {
        return PropertiesManager(
            Properties().apply {
                file.inputStream().use { load(it) }
            }
        )
    }
}

/**
 * Менеджер для работы со свойствами. Предоставляет методы для работы со строковыми значениями
 * */
@Suppress("UNCHECKED_CAST")
class PropertiesManager(val properties: Properties) {
    fun get(key: String): String {
        return properties.getProperty(key)
    }
    fun getOrDefault(key: String, defaultValue: String): String = properties.getOrDefault(key, defaultValue).toString()
    fun getMap(): Map<String, String> = properties.toMap() as Map<String, String>
}