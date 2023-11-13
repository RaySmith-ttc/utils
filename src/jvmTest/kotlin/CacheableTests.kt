import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ru.raysmith.utils.Cacheable
import java.util.concurrent.atomic.AtomicInteger
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

class CacheableTests {

    val value = AtomicInteger()

    val getter = {
        value.incrementAndGet()
        "foo"
    }

    @BeforeEach
    fun beforeEach() {
        value.set(0)
    }

    @Test
    fun `should returns correct value`() {
        val cacheable by Cacheable(getter = getter)

        assert(cacheable == "foo")
    }

    @Test
    fun `should returns cached value`() {
        val cacheable by Cacheable(getter = getter)

        println(cacheable)
        println(cacheable)

        assert(cacheable == "foo")
        assert(value.get() == 1)
    }

    @Test
    fun `should returns cached value when cache time is infinite`() {
        val cacheable by Cacheable(Duration.INFINITE, getter = getter)

        println(cacheable)
        println(cacheable)

        assert(cacheable == "foo")
        assert(value.get() == 1)
    }

    @Test
    fun `should returns new value every time when cache time is zero`() {
        val cacheable by Cacheable(Duration.ZERO, getter = getter)

        println(cacheable)
        println(cacheable)

        assert(cacheable == "foo")
        assert(value.get() == 3)
    }

    @Test
    fun `should updates cache when cache time is over`() {
        val cacheable by Cacheable(1.seconds, getter = getter)

        println(cacheable)
        println(cacheable)

        Thread.sleep(1000)

        assert(cacheable == "foo")
        assert(value.get() == 2)
    }
}