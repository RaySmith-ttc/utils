import org.junit.jupiter.api.Test
import ru.raysmith.utils.notNull
import ru.raysmith.utils.bothNull
import ru.raysmith.utils.orNotNull
import ru.raysmith.utils.orNull
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class InfixMethodsJvmTest {

    @Test
    fun notNull() {
        assertTrue { "" notNull "" }
        assertFalse { null notNull "" }
    }

    @Test
    fun bothNull() {
        assertTrue { null bothNull null }
        assertFalse { null bothNull "" }
    }

    @Test
    fun orNull() {
        assertTrue { null orNull null }
        assertTrue { null orNull "" }
        assertFalse { "" orNull "" }
    }

    @Test
    fun orNotNull() {
        assertTrue { "" orNotNull null }
        assertFalse { null orNotNull null }
    }
}