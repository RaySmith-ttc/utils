import org.junit.jupiter.api.Test
import ru.raysmith.utils.format
import ru.raysmith.utils.getParam
import java.net.URL
import kotlin.test.assertSame

class ExtensionsTest {

    @Test
    fun doubleFormat() {
        val value = 0.568912

        assert(value.format(2) == "0.57")
        assert(value.format(1) == "0.6")
        assert(value.format(0) == "1")
        assert(value.format(5) == "0.56891")
    }

    @Test
    fun findUrlParam() {
        val url = URL("https://example.org?foo=bar&bar=foo")
        assert(url.getParam("foo") == "bar")
    }
}