import org.junit.jupiter.api.*
import ru.raysmith.utils.properties.PropertiesFactory
import ru.raysmith.utils.properties.getOrNull
import java.io.File
import java.io.FileNotFoundException
import java.nio.file.Paths

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PropertiesJvmTest {

    companion object {
        const val PROPERTIES_FILE = "test.properties"
        const val NOT_CONTAINS_KEY = "not_contains"
    }

    @BeforeAll
    fun ascertainPropertiesFile() {
        assertDoesNotThrow {
            Paths.get(ClassLoader.getSystemResource(PROPERTIES_FILE).toURI())
        }
    }

    @Test
    fun propertyFromResources() {
        val properties = PropertiesFactory.from(PROPERTIES_FILE)
        assert(properties["foo"] == "bar")
        assert(properties.toMap().size == 3)
    }

    @Test
    fun propertyFromPath() {
        val properties = PropertiesFactory.from(Paths.get("src/jvmTest/path", PROPERTIES_FILE))
        assert(properties["foo"] == "bar")
        assert(properties.toMap().size == 1)
    }

    @Test
    fun propertyOrNull() {
        val properties = PropertiesFactory.from(PROPERTIES_FILE)
        assert(properties.getOrNull("not_contains_key") == null)
    }

    @Test
    fun propertyOrDefault() {
        val properties = PropertiesFactory.from(PROPERTIES_FILE)
        assert(properties.getOrDefault("foo", "none") == "bar")
        assert(properties.getOrDefault("foo", null) == "bar")
        assert(properties.getOrDefault(NOT_CONTAINS_KEY, "not_contains") == "not_contains")
        assert(properties.getOrDefault(NOT_CONTAINS_KEY, null) == null)
    }

    @Test
    fun fromOrNull() {
        val properties = PropertiesFactory.fromOrNull("notIncluded.properties")
        assert(properties == null)
    }

    @Test
    fun fileNotFoundTest() {
        val notIncludedFile = File("notIncluded.properties")
        val includedFile = Paths.get(ClassLoader.getSystemResource(PROPERTIES_FILE).toURI())

        assertThrows<FileNotFoundException> {
            PropertiesFactory.from("notIncluded.properties")
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