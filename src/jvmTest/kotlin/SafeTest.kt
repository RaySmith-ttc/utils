import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import ru.raysmith.utils.safe


class SafeTest {

    @Test
    @Suppress("IMPLICIT_NOTHING_TYPE_ARGUMENT_IN_RETURN_POSITION")
    fun shouldNotThrow() {
        assertDoesNotThrow {
            safe {
                error("should not throw")
            }
        }
    }

    @Test
    fun shouldReturnValue() {
        val value = safe { 1 }
        assert(value == 1)
    }

    @Test
    @Suppress("UNREACHABLE_CODE", "UNUSED_VARIABLE")
    fun shouldReturnNullOnError() {
        val value: Int? = safe<Int> { error("some error") }
        assert(value == null)
    }
}