import org.gradle.internal.impldep.org.junit.Before
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import ru.raysmith.utils.EndWordForNumNumberSide
import ru.raysmith.utils.endWordForNum
import ru.raysmith.utils.endWordForNumWithNumber

class EndWordTest {

    @Test fun endWordsIsCorrect() {
        println("Testing...")
        val titles = listOf("фотография", "фотографии", "фотографий")
        assertTrue { endWordForNum(1, titles) == "фотография" }
        assertTrue { endWordForNum(3, titles) == "фотографии" }
        assertTrue { endWordForNum(10, titles) == "фотографий" }
    }

    @Test fun endWordsWithNumberIsCorrect() {
        val titles = listOf("фотография", "фотографии", "фотографий")
        assertTrue { endWordForNumWithNumber(1, titles) == "1 фотография" }
    }

    @Test fun endWordsWithNumberRightIsCorrect() {
        val titles = listOf("фотография, в количестве", "фотографии, в количестве", "фотографий, в количестве")
        assertTrue { endWordForNumWithNumber(1, titles, EndWordForNumNumberSide.RIGHT) == "фотография, в количестве 1" }
    }

}