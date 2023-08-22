import org.junit.jupiter.api.Test
import ru.raysmith.utils.*
import java.net.URL
import java.time.LocalTime
import kotlin.test.assertEquals

class ExtensionsJvmTest {

    @Test
    fun findUrlParam() {
        val url = URL("https://example.org?foo=bar&bar=foo")
        assert(url.getParam("foo") == "bar")
        assert(url.getParam("no_contains") == null)
        assert(url.getParam("") == null)
    }

    @Test
    fun inPeriod() {
        val time = LocalTime.of(12, 0)

        assertEquals(time.inPeriod(LocalTime.of(10, 0), LocalTime.of(14, 0)), true)
        assertEquals(time.inPeriod(LocalTime.of(10, 0), LocalTime.of(11, 0)), false)
        assertEquals(time.inPeriod(LocalTime.of(20, 0), LocalTime.of(23, 0)), false)
        assertEquals(time.inPeriod(LocalTime.of(20, 0), LocalTime.of(11, 0)), false)
        assertEquals(time.inPeriod(LocalTime.of(12, 0), LocalTime.of(15, 0)), true)
        assertEquals(time.inPeriod(LocalTime.of(10, 0), LocalTime.of(12, 0)), true)
    }
}