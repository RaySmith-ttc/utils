package ru.raysmith.utils

import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

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
fun ByteArray.encodeToHexString(salt: String, algorithm: String): String {
    val key = SecretKeySpec(salt.toByteArray(), algorithm)
    val cipher = Cipher.getInstance(algorithm)
    cipher.init(Cipher.ENCRYPT_MODE, key)
    return cipher.doFinal(this).toHexString()
}