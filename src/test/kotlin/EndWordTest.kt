import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import ru.raysmith.utils.*
import kotlin.test.assertEquals

class EndWordTest {

    @Test
    fun endWordsIsCorrect() {
        val titles = listOf("фотография", "фотографии", "фотографий")
        assertAll("Окончания",
                {
                    assertEquals(endWordForNum(1, titles), "фотография")
                    assertEquals(endWordForNum(3, titles), "фотографии")
                    assertEquals(endWordForNum(10, titles), "фотографий")
                    assertEquals(endWordForNum(-10, titles), "фотографий")
                    assertEquals(endWordForNum(-3, titles), "фотографии")
                    assertEquals(endWordForNum(-1, titles), "фотография")
                    assertEquals(endWordForNum(0, titles), "фотографий")
                }
        )
    }

    @Test
    fun endWordsWithNumberIsCorrectWithSide() {
        val titles = listOf("фотография", "фотографии", "фотографий")
        assertEquals(1.endOfWord(titles), "1 фотография")
        assertEquals(1.endOfWord(titles), "1 фотография")
        assertEquals(1.endOfWord(titles, EndWordForNumNumberSide.LEFT), "1 фотография")
        assertEquals(1.endOfWord(titles, EndWordForNumNumberSide.RIGHT), "фотография 1")
        assertEquals(1L.endOfWord(titles, EndWordForNumNumberSide.RIGHT), "фотография 1")
        assertEquals(1.12.endOfWord(titles, EndWordForNumNumberSide.RIGHT), "фотография 1.12")
        assertEquals(5.endOfWord(titles, EndWordForNumNumberSide.RIGHT), "фотографий 5")
    }
}