import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ru.raysmith.utils.Cacheable
import java.util.concurrent.atomic.AtomicInteger
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

class CacheableTests {

    val value = AtomicInteger()

    val getter: () -> String get() = {
        value.incrementAndGet()
        "foo"
    }

    val nullableGetter: () -> String? get() = {
        value.incrementAndGet()
        null
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

        cacheable.hashCode()

        assert(value.get() == 1)
    }

    @Test
    fun `should returns cached value when cache time is infinite`() {
        val cacheable by Cacheable(Duration.INFINITE, getter = getter)

        cacheable.hashCode()

        assert(value.get() == 1)
    }

    @Test
    fun `should returns new value every time when cache time is zero`() {
        val cacheable by Cacheable(Duration.ZERO, getter = getter)

        cacheable.hashCode()
        cacheable.hashCode()
        cacheable.hashCode()

        assert(value.get() == 3)
    }

    @Test
    fun `should updates cache when cache time is over`() {
        val cacheable by Cacheable(1.seconds, getter = getter)

        cacheable.hashCode()
        Thread.sleep(1000)
        cacheable.hashCode()

        assert(value.get() == 2)
    }

    @Test
    fun `should return null with infinity cache`() {
        val cacheable by Cacheable(Duration.INFINITE, getter = nullableGetter)

        assert(cacheable == null)
    }

    @Test
    fun `should call getter once on value is null`() {
        val cacheable by Cacheable(Duration.INFINITE, getter = nullableGetter)

        cacheable.hashCode()
        assert(value.get() == 1)
        cacheable.hashCode()
        assert(value.get() == 1)
    }
}