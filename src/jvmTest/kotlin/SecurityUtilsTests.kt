import org.junit.jupiter.api.Test
import ru.raysmith.utils.decode
import ru.raysmith.utils.encode
import ru.raysmith.utils.encodeToHexString

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
}