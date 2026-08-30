import ru.raysmith.utils.allIndexed
import ru.raysmith.utils.anyIndexed
import ru.raysmith.utils.div
import ru.raysmith.utils.divAssign
import ru.raysmith.utils.forEachLet
import ru.raysmith.utils.ifOnlyOneOrNull
import ru.raysmith.utils.onlyOneOrNull
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

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

    @Test
    fun forEachLet() {
        val actual = mutableListOf<String>()
        listOf(1, 2, 3).forEachLet({ it + 1 }) { actual.add(it.toString()) }
        val expected = listOf("2", "3", "4")

        assertEquals(expected, actual)
    }

    @Test
    fun div() {
        assertEquals(setOf(1, 3), setOf(1, 2, 3) / 2)
        assertEquals(setOf(1, 2, 3), setOf(1, 3) / 2)

        assertEquals(listOf(1, 3), listOf(1, 2, 3) / 2)
        assertEquals(listOf(1, 3, 2), listOf(1, 3) / 2)

        assertEquals(hashSetOf(1, 3), hashSetOf(1, 2, 3) / 2)
        assertEquals(hashSetOf(1, 3, 2), hashSetOf(1, 3) / 2)
    }

    @Test
    fun divAssign() {
        val list = mutableListOf(1, 2, 3)
        list /= 2
        assertEquals(listOf(1, 3), list)
    }

    @Test
    fun allIndexed() {
        assertTrue(listOf<Int>().allIndexed { _, _ -> false })
        assertTrue(listOf("a", "b", "c").allIndexed { index, item -> item.first() == 'a' + index })
        assertFalse(listOf("a", "b", "x").allIndexed { index, item -> item.first() == 'a' + index })
        assertTrue(setOf(0, 1, 2).allIndexed { index, item -> index == item })
    }

    @Test
    fun anyIndexed() {
        assertFalse(listOf<Int>().anyIndexed { _, _ -> true })
        assertTrue(listOf("a", "b", "c").anyIndexed { index, item -> index == 1 && item == "b" })
        assertFalse(listOf("a", "b", "c").anyIndexed { index, item -> index == 3 || item == "d" })
        assertTrue(setOf(10, 20, 30).anyIndexed { index, item -> index == 2 && item == 30 })
    }
}
