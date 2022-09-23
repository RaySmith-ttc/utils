import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import ru.raysmith.utils.*
import java.net.URL
import java.time.LocalTime

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
        assert(url.getParam("no_contains") == null)
        assert(url.getParam("") == null)
    }

    @Test
    fun orNullIf() {
        assertNull("test".orNullIf { true })
        assertNotNull("test".orNullIf { false })
    }

    @Test
    fun inPeriod() {
        val time = LocalTime.of(12, 0)

        assertThat(time.inPeriod(LocalTime.of(10, 0), LocalTime.of(14, 0))).isEqualTo(true)
        assertThat(time.inPeriod(LocalTime.of(10, 0), LocalTime.of(11, 0))).isEqualTo(false)
        assertThat(time.inPeriod(LocalTime.of(20, 0), LocalTime.of(23, 0))).isEqualTo(false)
        assertThat(time.inPeriod(LocalTime.of(20, 0), LocalTime.of(11, 0))).isEqualTo(false)
        assertThat(time.inPeriod(LocalTime.of(12, 0), LocalTime.of(15, 0))).isEqualTo(true)
        assertThat(time.inPeriod(LocalTime.of(10, 0), LocalTime.of(12, 0))).isEqualTo(true)
    }

    @Test
    fun takeOrCut() {
        assertThat("Hello world".takeOrCut(100)).isEqualTo("Hello world")
        assertThat("Hello world".takeOrCut(5)).isEqualTo("Hello...")
        assertThat("Hello world".takeOrCut(0)).isEqualTo("...")
        assertThrows<IllegalArgumentException> {
            "Hello world".takeOrCut(-1)
        }
    }
}