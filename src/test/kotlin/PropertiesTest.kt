import org.junit.jupiter.api.*
import ru.raysmith.utils.PropertiesFactory
import java.io.File
import java.io.FileNotFoundException
import java.nio.file.Path
import java.nio.file.Paths

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PropertiesTest {

    companion object {
        const val PROPERTIES_FILE = "test.properties"
    }

    @BeforeAll
    fun ascertainPropertiesFile() {
        assertDoesNotThrow {
            Paths.get(ClassLoader.getSystemResource(PROPERTIES_FILE).toURI())
        }
    }

    @Test
    fun propertyFromResources() {
        val manager = PropertiesFactory.from(PROPERTIES_FILE)
        assert(manager.get("foo") == "bar")
        assert(manager.getMap().size == 2)
    }

    @Test
    fun propertyFromPath() {
        val manager = PropertiesFactory.from(Path.of("src/test/path", PROPERTIES_FILE))
        assert(manager.get("foo") == "bar")
        assert(manager.getMap().size == 1)
    }

    @Test
    fun fileNotFoundTest() {
        val notIncludedFile = File("notIncludedFile.txt")
        val includedFile = Paths.get(ClassLoader.getSystemResource(PROPERTIES_FILE).toURI())

        assertThrows<FileNotFoundException> {
            PropertiesFactory.from("notIncludedFile.txt")
            PropertiesFactory.from(notIncludedFile)
            PropertiesFactory.from(notIncludedFile.toPath())
        }
        assertDoesNotThrow {
            PropertiesFactory.from(PROPERTIES_FILE)
            PropertiesFactory.from(includedFile)
            PropertiesFactory.from(includedFile.toFile())
        }

    }
}