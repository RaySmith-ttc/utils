import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import ru.raysmith.utils.BigDecimalProgression
import ru.raysmith.utils.downTo
import ru.raysmith.utils.eq
import ru.raysmith.utils.step
import java.math.BigDecimal
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class BigDecimalProgressionTests {

    @Test
    fun downToDefaultStep() {
        val progression = BigDecimal("5") downTo BigDecimal("1")
        assertEquals(BigDecimal("5"), progression.first)
        assertEquals(BigDecimal("1"), progression.last)
        assertEquals(BigDecimal("-1"), progression.step)

        val list = progression.toList()
        assertEquals(listOf(BigDecimal("5"), BigDecimal("4"), BigDecimal("3"), BigDecimal("2"), BigDecimal("1")), list)
    }

    @Test
    fun downToWithDecimalScale() {
        val progression = BigDecimal("1.0") downTo BigDecimal("0.7")
        val list = progression.toList()
        assertTrue(list.eq(listOf(BigDecimal("1.0"), BigDecimal("0.9"), BigDecimal("0.8"), BigDecimal("0.7"))))
    }

    @Test
    fun closedRangeWithStep() {
        val range = BigDecimal("1.0")..BigDecimal("2.0")
        val progression = range step BigDecimal("0.3")

        assertEquals(BigDecimal("1.0"), progression.first)
        assertTrue(BigDecimal("1.9").eq(progression.last))
        assertEquals(BigDecimal("0.3"), progression.step)

        val list = progression.toList()
        assertTrue(list.eq(listOf(BigDecimal("1.0"), BigDecimal("1.3"), BigDecimal("1.6"), BigDecimal("1.9"))))
    }

    @Test
    fun closedRangeWithStepBackwards() {
        val range = BigDecimal("2.0") downTo BigDecimal("1.0")
        val progression = range step BigDecimal("0.3")

        assertEquals(BigDecimal("2.0"), progression.first)
        assertTrue(BigDecimal("1.1").eq(progression.last))
        assertEquals(BigDecimal("-0.3"), progression.step)

        val list = progression.toList()
        assertTrue(list.eq(listOf(BigDecimal("2.0"), BigDecimal("1.7"), BigDecimal("1.4"), BigDecimal("1.1"))))
    }

    @Test
    fun emptyProgression() {
        val emptyProgression = BigDecimal("1") downTo BigDecimal("5")
        assertTrue(emptyProgression.isEmpty())
        assertTrue(emptyProgression.toList().isEmpty())

        val emptyWithStep = (BigDecimal("1") downTo BigDecimal("5")) step BigDecimal("1")
        assertTrue(emptyWithStep.isEmpty())
        assertTrue(emptyWithStep.toList().isEmpty())

        val emptyExplicit = BigDecimalProgression.fromClosedRange(BigDecimal("5"), BigDecimal("1"), BigDecimal("1"))
        assertTrue(emptyExplicit.isEmpty())
        assertTrue(emptyExplicit.toList().isEmpty())
    }

    @Test
    fun closedRangeWithStepReversed() {
        val range = BigDecimal("5")..BigDecimal("1")
        val progression = range step BigDecimal("1")
        assertFalse(progression.isEmpty())
        assertEquals(listOf(BigDecimal("5"), BigDecimal("4"), BigDecimal("3"), BigDecimal("2"), BigDecimal("1")), progression.toList())
    }

    @Test
    fun validationThrows() {
        assertThrows<IllegalArgumentException> {
            (BigDecimal("1")..BigDecimal("5")) step BigDecimal("0")
        }

        assertThrows<IllegalArgumentException> {
            (BigDecimal("1")..BigDecimal("5")) step BigDecimal("-1")
        }

        assertThrows<IllegalArgumentException> {
            // Different scale: start scale 0, end scale 2
            (BigDecimal("1")..BigDecimal("5.00")) step BigDecimal("1")
        }
    }
}
