import org.junit.jupiter.api.Test
import ru.raysmith.utils.PropertiesFactory
import java.nio.file.Path

class PropertiesTest {

    @Test
    fun propertyFromResources() {
        val manager = PropertiesFactory.from("test.properties")
        assert(manager.get("foo") == "bar")
        assert(manager.getMap().size == 2)
    }

    @Test
    fun propertyFromPath() {
        val manager = PropertiesFactory.from(Path.of("src/test/path", "test.properties"))
        assert(manager.get("foo") == "bar")
        assert(manager.getMap().size == 1)
    }
}