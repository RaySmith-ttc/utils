import org.junit.jupiter.api.Test
import ru.raysmith.utils.decode
import ru.raysmith.utils.encode
import ru.raysmith.utils.encodeToHexString
import ru.raysmith.utils.sha256
import ru.raysmith.utils.encryptAesGcm
import ru.raysmith.utils.decryptAesGcm
import ru.raysmith.utils.hmacSHA256
import ru.raysmith.utils.hmacSHA512
import ru.raysmith.utils.randomHexString
import kotlin.test.assertEquals

class SecurityUtilsTests {

    @OptIn(ExperimentalStdlibApi::class)
    @Test
    fun decodeAndEncode() {
        val alghoritm = "Blowfish"
        val salt = "rCbw5xQ3hNzcqLBDsjEdVg"

        val bytes = "Hello world!".toByteArray()

        val encoded = bytes.encode(salt, alghoritm)
        val decoded = encoded.decode(salt, alghoritm)

        assert(encoded.toHexString() == "c91f6cca00a3f32284b9336509966f2d")
        assert(decoded.contentEquals("Hello world!".toByteArray()))
    }

    @OptIn(ExperimentalStdlibApi::class)
    @Test
    fun toHexString() {
        val alghoritm = "Blowfish"
        val salt = "rCbw5xQ3hNzcqLBDsjEdVg"

        val string = "Hello world!"
        val bytes = string.toByteArray()

        val encoded = bytes.encodeToHexString(salt, alghoritm)
        val decoded = encoded.hexToByteArray().decode(salt, alghoritm)

        assert(encoded == "c91f6cca00a3f32284b9336509966f2d")
        assert(String(decoded) == string)
    }

    @Test
    fun sha256() {
        val input = "Hello world!"
        val salt = "123"

        val hash = input.sha256(salt)

        assert(hash == "57f37aaa53b4355762cd5209c435870ef8fb5e0d355a9715c67a0d6d3bb52708")
    }

    @OptIn(ExperimentalStdlibApi::class)
    @Test
    fun randomHexStringTest() {
        val length = 16
        val hex1 = randomHexString(length)
        val hex2 = randomHexString(length)

        assert(hex1.length == length * 2)
        assert(hex2.length == length * 2)
        assert(hex1 != hex2)
        assert(hex1.matches(Regex("[0-9a-f]+")))
    }

    @OptIn(ExperimentalStdlibApi::class)
    @Test
    fun hmacTest() {
        val key = "secret"
        val message = "Hello world!"

        val hmac256 = message.hmacSHA256(key)
        val hmac512 = message.hmacSHA512(key)

        assert(hmac256.length == 64) // 32 bytes * 2
        assert(hmac512.length == 128) // 64 bytes * 2
        
        assertEquals("a65f4cfcf5f421ff2be052e0642bccbcfeb126ee73ebc4fe3b381964302eb632", hmac256)
        assertEquals("dff6c00943387f9039566bfee0994de698aa2005eecdbf12d109e17aff5bbb1b022347fbf4bd94ede7c7d51571022525556b64f9d5e4386de99d0025886eaaff", hmac512)
    }

    @OptIn(ExperimentalStdlibApi::class)
    @Test
    fun aesGcmTest() {
        // Password-based
        val password = "securePassword123"
        val message = "Sensitive Data"

        val encryptedExtension = message.encryptAesGcm(password)
        
        assert(encryptedExtension != message)
        
        val decrypted = encryptedExtension.decryptAesGcm(password)
        assert(decrypted == message)

        // Key-based
        val keyBytes = ByteArray(32)
        java.security.SecureRandom().nextBytes(keyBytes)
        val key = javax.crypto.spec.SecretKeySpec(keyBytes, "AES")

        val encryptedKey = message.encryptAesGcm(key)
        val decryptedKey = encryptedKey.decryptAesGcm(key)
        
        assert(decryptedKey == message)

        val tampered = encryptedExtension.substring(0, encryptedExtension.length - 1) + "A"
        try {
            tampered.decryptAesGcm(password)
            assert(false) { "Should throw AEADBadTagException or similar" }
        } catch (e: Exception) {
            // Expected
            assert(e is javax.crypto.AEADBadTagException || e is IllegalArgumentException || e is java.security.GeneralSecurityException)
        }
    }
}
