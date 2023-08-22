import ru.raysmith.utils.*
import kotlin.test.*

class ExtensionsTest {

    @Test
    fun doubleFormat() {
        val value = 0.568912

        assertEquals(value.format(2), "0.57")
        assertEquals(value.format(1), "0.6")
        assertEquals(value.format(0), "1")
        assertEquals(value.format(5), "0.56891")
    }

    @Test
    fun orNullIf() {
        assertNull("test".orNullIf { true })
        assertNotNull("test".orNullIf { false })
    }

    @Test
    fun takeOrCut() {
        assertEquals("Hello world".takeOrCut(100), "Hello world")
        assertEquals("Hello world".takeOrCut(5), "Hello...")
        assertEquals("Hello world".takeOrCut(0), "...")
        assertFails {
            "Hello world".takeOrCut(-1)
        }
    }
}