import kotlin.test.Test
import kotlin.test.assertEquals

class UUIDTests {

    @Test
    fun uuid() {
        assertEquals(ru.raysmith.utils.uuid().matches("^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$".toRegex()), true)
    }
}