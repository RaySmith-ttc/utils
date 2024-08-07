import ru.raysmith.utils.*
import kotlin.test.*

class ExtensionsTest {

    @Test
    fun doubleFormat() {
        val value = 0.568912

        assertEquals("0.57", value.format(2))
        assertEquals("0.6", value.format(1))
        assertEquals("1", value.format(0))
        assertEquals("0.56891", value.format(5))

        assertEquals("0", 0.1.format(0))
    }

    @Test
    fun doubleRound() {
        val value = 0.568912

        assertEquals(0.57, value.round(2))
        assertEquals(0.6, value.round(1))
        assertEquals(1.0, value.round(0))
        assertEquals(0.56891, value.round(5))

        assertEquals(0.0, 0.1.round(0))
    }

    @Test
    fun orNullIf() {
        assertNull("test".orNullIf { true })
        assertNotNull("test".orNullIf { false })
    }

    @Test
    fun takeOrCut() {
        assertEquals("Hello world", "Hello world".takeOrCut(100))
        assertEquals("Hello…", "Hello world".takeOrCut(6))
        assertEquals("Hello…", "Hello world".takeOrCut(5, countDots = false))
        assertEquals("", "Hello world".takeOrCut(0))
        assertEquals("…", "Hello world".takeOrCut(0, countDots = false))
        assertFails {
            "Hello world".takeOrCut(-1)
        }
    }

    @Test
    fun bytes() {
        assertEquals("0 b", (-1).bytesToString())
        assertEquals("0 b", 0.bytesToString())
        assertEquals("1 b", 1.bytesToString())
        assertEquals("2 kb", 2048.bytesToString())
        assertEquals("2 gb", Int.MAX_VALUE.bytesToString())
        assertEquals("8192 pb", Long.MAX_VALUE.bytesToString())
    }
}