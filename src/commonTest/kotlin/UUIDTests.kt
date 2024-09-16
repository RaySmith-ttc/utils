import kotlin.test.Test
import kotlin.test.assertEquals

class UUIDTests {

    @Test
    fun uuid() {
        assertEquals(ru.raysmith.utils.uuid().matches("^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$".toRegex()), true)
        assertEquals(ru.raysmith.utils.uuid(zero = true), "00000000-0000-0000-0000-000000000000")
    }
}