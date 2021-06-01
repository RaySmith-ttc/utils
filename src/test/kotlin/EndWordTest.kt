import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import ru.raysmith.utils.EndWordForNumNumberSide
import ru.raysmith.utils.endWordForNum
import ru.raysmith.utils.endWordForNumWithNumber
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
        assertEquals(endWordForNumWithNumber(1, titles), "1 фотография")
        assertEquals(endWordForNumWithNumber(1, titles, EndWordForNumNumberSide.LEFT), "1 фотография")
        assertEquals(endWordForNumWithNumber(1, titles, EndWordForNumNumberSide.RIGHT), "фотография 1")
        assertEquals(endWordForNumWithNumber(5, titles, EndWordForNumNumberSide.RIGHT), "фотографий 5")
    }
}