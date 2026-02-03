package ru.raysmith.utils

import java.security.MessageDigest
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import kotlin.text.toHexString

/**
 * Шифрует массив байтов с использованием указанной соли и алгоритма.
 *
 * @param salt соль для шифрования
 * @param algorithm алгоритм шифрования
 * @return зашифрованный массив байтов
 */
fun ByteArray.encode(salt: String, algorithm: String): ByteArray {
    val key = SecretKeySpec(salt.toByteArray(), algorithm)
    val cipher = Cipher.getInstance(algorithm)
    cipher.init(Cipher.ENCRYPT_MODE, key)
    return cipher.doFinal(this)
}

/**
 * Дешифрует массив байтов с использованием указанной соли и алгоритма.
 *
 * @param salt соль для дешифрования
 * @param algorithm алгоритм шифрования
 * @return расшифрованный массив байтов
 */
fun ByteArray.decode(salt: String, algorithm: String): ByteArray {
    val key = SecretKeySpec(salt.toByteArray(), algorithm)
    val cipher = Cipher.getInstance(algorithm)
    cipher.init(Cipher.DECRYPT_MODE, key)
    return cipher.doFinal(this)
}

/**
 * Шифрует массив байтов и возвращает его представление в виде строки в шестнадцатеричном формате.
 *
 * @param salt соль для шифрования
 * @param algorithm алгоритм шифрования
 * @return строковое представление зашифрованных данных в шестнадцатеричном формате
 */
@OptIn(ExperimentalStdlibApi::class)
fun ByteArray.encodeToHexString(salt: String, algorithm: String, format: HexFormat = HexFormat.Default): String {
    val key = SecretKeySpec(salt.toByteArray(), algorithm)
    val cipher = Cipher.getInstance(algorithm)
    cipher.init(Cipher.ENCRYPT_MODE, key)
    return cipher.doFinal(this).toHexString(format)
}

/**
 * Хэширует строку, используя указанную [соль][salt] и SHA-256
 *
 * @return хеш строки
 */
@OptIn(ExperimentalStdlibApi::class)
fun String.sha256(salt: String, format: HexFormat = HexFormat.Default): String {
    val digest = MessageDigest.getInstance("SHA-256")
    val hash = digest.digest(salt.toByteArray() + this.toByteArray())
    return hash.toHexString(format)
}

/**
 * Создает HMAC SHA-256 хэш строки с использованием указанного ключа
 *
 * @param key ключ для HMAC
 * @return HMAC SHA-256 хэш в шестнадцатеричном формате
 */
@OptIn(ExperimentalStdlibApi::class)
fun String.hmacSHA256(key: String, format: HexFormat = HexFormat.Default) = hmac("HmacSHA256", key).toHexString(format)

/**
 * Создает HMAC SHA-512 хэш строки с использованием указанного ключа
 *
 * @param key ключ для HMAC
 * @return HMAC SHA-512 хэш в шестнадцатеричном формате
 */
@OptIn(ExperimentalStdlibApi::class)
fun String.hmacSHA512(key: String, format: HexFormat = HexFormat.Default) = hmac("HmacSHA512", key).toHexString(format)

/**
 * Создает HMAC хэш строки с использованием указанного алгоритма и ключа
 *
 * @param algorithm алгоритм HMAC (например, HmacSHA256, HmacSHA512)
 * @param key ключ для HMAC
 * @return HMAC хэш в шестнадцатеричном формате
 */
fun String.hmac(algorithm: String, key: String): ByteArray {
    val mac = Mac.getInstance(algorithm)
    mac.init(SecretKeySpec(key.toByteArray(), algorithm))
    return mac.doFinal(toByteArray())
}

/**
 * Генерирует криптографически стойкую случайную строку в шестнадцатеричном формате
 *
 * @param length длина возвращаемой строки. По умолчанию 32 (256 бит)
 * @return случайная строка в шестнадцатеричном формате
 */
@OptIn(ExperimentalStdlibApi::class)
fun secureRandomHex(length: Int = 32, format: HexFormat = HexFormat.Default): String {
    val random = SecureRandom()
    val bytes = ByteArray(length)
    random.nextBytes(bytes)
    return bytes.toHexString(format)
}