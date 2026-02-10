package ru.raysmith.utils

import java.security.MessageDigest
import java.security.SecureRandom
import java.util.Base64
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
 * Hashes a string using the specified [salt] and SHA-256.
 *
 * @return SHA-256 hash of the string
 */
@Deprecated("Use hmacSHA256 for keyed hashing or MessageDigest directly.", ReplaceWith("hmacSHA256(salt, format)"))
@OptIn(ExperimentalStdlibApi::class)
fun String.sha256(salt: String, format: HexFormat = HexFormat.Default): String {
    val digest = MessageDigest.getInstance("SHA-256")
    val hash = digest.digest(salt.toByteArray(Charsets.UTF_8) + this.toByteArray(Charsets.UTF_8))
    return hash.toHexString(format)
}

/**
 * Creates an HMAC hash of the string using the specified algorithm and key.
 *
 * @param algorithm HMAC algorithm (e.g. HmacSHA256, HmacSHA512)
 * @param key HMAC key
 * @return HMAC hash as a byte array
 *
 * @see hmacSHA256
 * @see hmacSHA512
 */
fun String.hmac(algorithm: String, key: String): ByteArray {
    val mac = Mac.getInstance(algorithm)
    mac.init(SecretKeySpec(key.toByteArray(Charsets.UTF_8), algorithm))
    return mac.doFinal(toByteArray(Charsets.UTF_8))
}

/**
 * Creates an HMAC SHA-256 hash of the string using the specified key.
 *
 * @param key HMAC key
 * @return HMAC SHA-256 hash in hexadecimal format
 */
@OptIn(ExperimentalStdlibApi::class)
fun String.hmacSHA256(key: String, format: HexFormat = HexFormat.Default) = hmac("HmacSHA256", key).toHexString(format)

/**
 * Creates an HMAC SHA-512 hash of the string using the specified key.
 *
 * @param key HMAC key
 * @return HMAC SHA-512 hash in hexadecimal format
 */
@OptIn(ExperimentalStdlibApi::class)
fun String.hmacSHA512(key: String, format: HexFormat = HexFormat.Default) = hmac("HmacSHA512", key).toHexString(format)

/**
 * Generates a cryptographically strong random hexadecimal string.
 *
 * @param byteLength number of random bytes to generate. Default 32 (256 bits), resulting in a 64-character string.
 * @return random hexadecimal string
 */
@OptIn(ExperimentalStdlibApi::class)
fun randomHexString(byteLength: Int = 32, format: HexFormat = HexFormat.Default): String {
    val random = SecureRandom()
    val bytes = ByteArray(byteLength)
    random.nextBytes(bytes)
    return bytes.toHexString(format)
}

/**
 * Generates a cryptographically strong random hexadecimal string.
 *
 * @param length number of random bytes to generate (ambiguous name). Default 32 (256 bits).
 * @return random hexadecimal string
 */
@Deprecated("Ambiguous length parameter. Use randomHexString(byteLength).", ReplaceWith("randomHexString(length, format)"))
@OptIn(ExperimentalStdlibApi::class)
fun secureRandomHex(length: Int = 32, format: HexFormat = HexFormat.Default): String {
    return randomHexString(length, format)
}

// --------------------------------------------- AES-GCM Implementation ------------------------------------------------

private const val AES_GCM_ALGORITHM = "AES/GCM/NoPadding"
private const val GCM_IV_LENGTH = 12
private const val GCM_TAG_LENGTH = 128

/**
 * Encrypts the string using AES-GCM (Authenticated Encryption).
 * The result is URL-safe Base64 string containing both the IV and the Ciphertext.
 *
 * @param secretKey secret key (must be 16, 24, or 32 bytes for AES-128/192/256)
 * @return URL-safe Base64 encrypted string
 */
fun String.encryptAesGcm(secretKey: SecretKey): String {
    val cipher = Cipher.getInstance(AES_GCM_ALGORITHM)
    val iv = ByteArray(GCM_IV_LENGTH)
    SecureRandom().nextBytes(iv)
    val spec = GCMParameterSpec(GCM_TAG_LENGTH, iv)
    cipher.init(Cipher.ENCRYPT_MODE, secretKey, spec)

    val cipherText = cipher.doFinal(this.toByteArray(Charsets.UTF_8))
    val combined = iv + cipherText
    
    return Base64.getUrlEncoder().withoutPadding().encodeToString(combined)
}

/**
 * Helper to encrypt using a string key. The key is hashed to 32 bytes to ensure valid AES-256 key size.
 */
fun String.encryptAesGcm(password: String): String {
    val keyBytes = MessageDigest.getInstance("SHA-256").digest(password.toByteArray(Charsets.UTF_8))
    return this.encryptAesGcm(SecretKeySpec(keyBytes, "AES"))
}

/**
 * Decrypts a URL-safe Base64 string using AES-GCM.
 *
 * @param secretKey secret key used for encryption
 * @return original decrypted string
 * @throws javax.crypto.AEADBadTagException if the key is wrong or the data was tampered with
 */
fun String.decryptAesGcm(secretKey: SecretKey): String {
    val decoded = Base64.getUrlDecoder().decode(this)
    
    // Extract IV
    if (decoded.size < GCM_IV_LENGTH) throw IllegalArgumentException("Invalid encrypted data length")
    val iv = decoded.copyOfRange(0, GCM_IV_LENGTH)
    val cipherText = decoded.copyOfRange(GCM_IV_LENGTH, decoded.size)

    val cipher = Cipher.getInstance(AES_GCM_ALGORITHM)
    val spec = GCMParameterSpec(GCM_TAG_LENGTH, iv)
    cipher.init(Cipher.DECRYPT_MODE, secretKey, spec)

    return String(cipher.doFinal(cipherText), Charsets.UTF_8)
}

/**
 * Helper to decrypt using a string key.
 */
fun String.decryptAesGcm(password: String): String {
    val keyBytes = MessageDigest.getInstance("SHA-256").digest(password.toByteArray(Charsets.UTF_8))
    return this.decryptAesGcm(SecretKeySpec(keyBytes, "AES"))
}
