import org.junit.jupiter.api.Test
import ru.raysmith.utils.*
import java.net.URL
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import kotlin.test.assertEquals
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.nanoseconds

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

    @Test
    fun TemporalPlus() {
        val date = LocalDateTime.of(2000, 1, 1, 0, 0, 0)

        assertEquals(date + 50.days, LocalDateTime.of(2000, 2, 20, 0, 0, 0))
        assertEquals(date + 1.nanoseconds, LocalDateTime.of(2000, 1, 1, 0, 0, 0, 1))
    }
}