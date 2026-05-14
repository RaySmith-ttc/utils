package ru.raysmith.utils

import java.security.MessageDigest
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.Mac
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.SecretKeySpec
import kotlin.text.toHexString

/**
 * Encrypts a byte array using the specified salt and algorithm.
 *
 * @param salt encryption salt (used as key)
 * @param algorithm encryption algorithm
 * @return encrypted byte array
 */
@Deprecated("Unsafe implementation. Use standard Cipher with proper key derivation (e.g. PBKDF2) and random IV.")
fun ByteArray.encode(salt: String, algorithm: String): ByteArray {
    val key = SecretKeySpec(salt.toByteArray(), algorithm)
    val cipher = Cipher.getInstance(algorithm)
    cipher.init(Cipher.ENCRYPT_MODE, key)
    return cipher.doFinal(this)
}

/**
 * Decrypts a byte array using the specified salt and algorithm.
 *
 * @param salt decryption salt (used as key)
 * @param algorithm encryption algorithm
 * @return decrypted byte array
 */
@Deprecated("Unsafe implementation. Use standard Cipher with proper key derivation (e.g. PBKDF2) and random IV.")
fun ByteArray.decode(salt: String, algorithm: String): ByteArray {
    val key = SecretKeySpec(salt.toByteArray(), algorithm)
    val cipher = Cipher.getInstance(algorithm)
    cipher.init(Cipher.DECRYPT_MODE, key)
    return cipher.doFinal(this)
}

/**
 * Encrypts a byte array and returns its hexadecimal string representation.
 *
 * @param salt encryption salt (used as key)
 * @param algorithm encryption algorithm
 * @return hexadecimal string representation of encrypted data
 */
@Deprecated("Unsafe implementation. Use standard Cipher with proper key derivation (e.g. PBKDF2) and random IV.")
@OptIn(ExperimentalStdlibApi::class)
fun ByteArray.encodeToHexString(salt: String, algorithm: String, format: HexFormat = HexFormat.Default): String {
    val key = SecretKeySpec(salt.toByteArray(), algorithm)
    val cipher = Cipher.getInstance(algorithm)
    cipher.init(Cipher.ENCRYPT_MODE, key)
    return cipher.doFinal(this).toHexString(format)
}

/**
 * Hashes a byte array using the specified [salt] and SHA-256.
 *
 * @return SHA-256 hash as a byte array
 */
@OptIn(ExperimentalStdlibApi::class)
fun ByteArray.sha256(salt: ByteArray): ByteArray {
    val digest = MessageDigest.getInstance("SHA-256")
    val hash = digest.digest(salt + this)
    return hash
}

/**
 * Creates an HMAC hash of the byte array using the specified algorithm and key.
 *
 * @param algorithm HMAC algorithm (e.g. HmacSHA256, HmacSHA512)
 * @param key HMAC key
 * @return HMAC hash as a byte array
 *
 * @see hmacSHA256
 * @see hmacSHA512
 */
fun ByteArray.hmac(algorithm: String, key: ByteArray): ByteArray {
    val mac = Mac.getInstance(algorithm)
    mac.init(SecretKeySpec(key, algorithm))
    return mac.doFinal(this)
}

/**
 * Creates an HMAC SHA-256 hash of the byte array using the specified key.
 *
 * @param key HMAC key
 * @return HMAC SHA-256 hash as a byte array
 */
@OptIn(ExperimentalStdlibApi::class)
fun ByteArray.hmacSHA256(key: ByteArray) = hmac("HmacSHA256", key)

/**
 * Creates an HMAC SHA-256 hash of the string using the specified key and returns it as a hexadecimal string.
 *
 * @param key HMAC key
 * @return HMAC SHA-256 as a hexadecimal string
 * */
fun String.hmacSHA256(key: String, format: HexFormat = HexFormat.Default): String {
    return toByteArray().hmac("HmacSHA256", key.toByteArray()).toHexString(format)
}

/**
 * Creates an HMAC SHA-512 hash of the string using the specified key.
 *
 * @param key HMAC key
 * @return HMAC SHA-512 as a byte array
 */
@OptIn(ExperimentalStdlibApi::class)
fun ByteArray.hmacSHA512(key: ByteArray) = hmac("HmacSHA512", key)

/**
 * Creates an HMAC SHA-512 hash of the string using the specified key and returns it as a hexadecimal string.
 *
 * @param key HMAC key
 * @return HMAC SHA-512 as a hexadecimal string
 * */
fun String.hmacSHA512(key: String, format: HexFormat = HexFormat.Default): String {
    return toByteArray().hmac("HmacSHA512", key.toByteArray()).toHexString(format)
}

/**
 * Generates a cryptographically strong random byte array.
 *
 * @param byteLength number of random bytes to generate. Default 32 (256 bits), resulting in a 64-character string.
 * @return random byte array
 */
@OptIn(ExperimentalStdlibApi::class)
fun randomHexString(byteLength: Int = 32, format: HexFormat = HexFormat.Default): String {
    val random = SecureRandom()
    val bytes = ByteArray(byteLength)
    random.nextBytes(bytes)
    return bytes.toHexString(format)
}

/**
 * Generates a cryptographically strong random byte array.
 *
 * @param length number of random bytes to generate. Default 32 (256 bits).
 * @return random byte array
 * */
fun randomByteArray(length: Int = 32): ByteArray {
    val random = SecureRandom()
    val bytes = ByteArray(length)
    random.nextBytes(bytes)
    return bytes
}

/**
 * Generates a cryptographically strong random hexadecimal string.
 *
 * @param length number of random bytes to generate (ambiguous name). Default 32 (256 bits).
 * @return random hexadecimal string
 */
@Deprecated("Ambiguous length parameter. Use randomHexString(byteLength).", ReplaceWith("randomHexString(length, format)"))
@OptIn(ExperimentalStdlibApi::class)
fun secureRandomHex(length: Int = 32): String {
    return randomHexString(length)
}

// --------------------------------------------- AES-GCM Implementation ------------------------------------------------

private const val AES_GCM_ALGORITHM = "AES/GCM/NoPadding"
private const val GCM_IV_LENGTH = 12
private const val GCM_TAG_LENGTH = 128

/**
 * Encrypts the bytes using AES-GCM (Authenticated Encryption).
 * The result is URL-safe Base64 string containing both the IV and the Ciphertext.
 *
 * @param secretKey secret key (must be 16, 24, or 32 bytes for AES-128/192/256)
 * @return URL-safe Base64 encrypted string
 */
fun ByteArray.encryptAesGcm(secretKey: SecretKey): ByteArray {
    val cipher = Cipher.getInstance(AES_GCM_ALGORITHM)
    val iv = ByteArray(GCM_IV_LENGTH)
    SecureRandom().nextBytes(iv)
    val spec = GCMParameterSpec(GCM_TAG_LENGTH, iv)
    cipher.init(Cipher.ENCRYPT_MODE, secretKey, spec)

    val cipherText = cipher.doFinal(this)
    return iv + cipherText
}

/**
 * Helper to encrypt using a string key. The key is hashed to 32 bytes to ensure valid AES-256 key size.
 */
fun ByteArray.encryptAesGcm(password: ByteArray): ByteArray {
    val keyBytes = MessageDigest.getInstance("SHA-256").digest(password)
    return this.encryptAesGcm(SecretKeySpec(keyBytes, "AES"))
}

/**
 * Decrypts a URL-safe Base64 string using AES-GCM.
 *
 * @param secretKey secret key used for encryption
 * @return original decrypted string
 * @throws javax.crypto.AEADBadTagException if the key is wrong or the data was tampered with
 */
fun ByteArray.decryptAesGcm(secretKey: SecretKey): ByteArray {
    
    // Extract IV
    if (size < GCM_IV_LENGTH) throw IllegalArgumentException("Invalid encrypted data length")
    val iv = copyOfRange(0, GCM_IV_LENGTH)
    val cipherByteArray = copyOfRange(GCM_IV_LENGTH, size)

    val cipher = Cipher.getInstance(AES_GCM_ALGORITHM)
    val spec = GCMParameterSpec(GCM_TAG_LENGTH, iv)
    cipher.init(Cipher.DECRYPT_MODE, secretKey, spec)

    return cipher.doFinal(cipherByteArray)
}

/**
 * Helper to decrypt using a string key.
 */
fun ByteArray.decryptAesGcm(password: ByteArray): ByteArray {
    val keyBytes = MessageDigest.getInstance("SHA-256").digest(password)
    return this.decryptAesGcm(SecretKeySpec(keyBytes, "AES"))
}
