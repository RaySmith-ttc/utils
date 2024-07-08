import ru.raysmith.utils.ifOnlyOneOrNull
import ru.raysmith.utils.onlyOneOrNull
import kotlin.test.Test
import kotlin.test.assertEquals

class CollectionExtensionsTests {

    @Test
    fun onlyOneOrNull() {
        assertEquals(null, listOf<Any>().onlyOneOrNull())
        assertEquals(null, listOf<Any>(1, 2).onlyOneOrNull())
        assertEquals(1, listOf<Any>(1).onlyOneOrNull())
    }

    @Test
    fun ifOnlyOneOrNull() {
        assertEquals(null, listOf<Any>().ifOnlyOneOrNull { true })
        assertEquals(null, listOf<Any>(1, 2).ifOnlyOneOrNull { true })
        assertEquals(null, listOf<Any>(1).ifOnlyOneOrNull { it == 2 })
        assertEquals(1, listOf<Any>(1).ifOnlyOneOrNull { it == 1 })
    }
}