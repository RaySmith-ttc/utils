import org.junit.jupiter.api.Test

class StringUtilsTests {

    @Test
    fun generateRandomString() {
        assert(ru.raysmith.utils.generateRandomString(10).length == 10)
        assert(ru.raysmith.utils.generateRandomString(10, listOf('a')) == "aaaaaaaaaa")
        assert(ru.raysmith.utils.generateRandomString(10).groupBy { it }.size != 1)

    }
}