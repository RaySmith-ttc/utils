import org.junit.jupiter.api.assertDoesNotThrow
import ru.raysmith.utils.asResource
import ru.raysmith.utils.asResourceStream
import kotlin.test.Test

class ResourcesTests {

    @Test
    fun stringAsResource() {
        assertDoesNotThrow {
            "test.properties".asResource()
        }
    }

    @Test
    fun stringAsResourceStream() {
        assertDoesNotThrow {
            "test.properties".asResourceStream()
        }
    }
}