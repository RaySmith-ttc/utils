import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertTimeout
import ru.raysmith.utils.ms
import ru.raysmith.utils.sync
import kotlin.concurrent.thread
import kotlin.properties.Delegates
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.time.toJavaDuration

class SyncDelegateTests {

    val delegate = Delegates.sync<String?>(null)
    var value by delegate

    @Test
    fun syncDelegate() {
        value = "123"
        assertEquals("123", value)

        thread {
            synchronized(delegate.lock) {
                runBlocking {
                    delay(200)
                }
            }
        }

        runBlocking {
            delay(50)
        }

        assertFails {
            runBlocking {
                assertTimeout(100.ms.toJavaDuration()) {
                    value = "124"
                }
            }
        }

    }

}