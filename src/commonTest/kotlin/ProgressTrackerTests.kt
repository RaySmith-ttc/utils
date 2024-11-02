import kotlin.test.Test
import ru.raysmith.utils.ProgressTracker
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertTrue

class ProgressTrackerTests {

    @Test
    fun testElementsLessThan100() {
        val elements = 50
        val percentages = mutableListOf<Int>()
        val tracker = ProgressTracker(elements) { percent ->
            percentages.add(percent)
        }

        repeat(elements) {
            tracker.incIterate()
        }

        assertEquals(100, percentages.last())
        assertTrue(percentages.size <= 100)
    }

    @Test
    fun testElementsGreaterThan100() {
        val elements = 150
        val percentages = mutableListOf<Int>()
        val tracker = ProgressTracker(elements) { percent ->
            percentages.add(percent)
        }

        repeat(elements) {
            tracker.incIterate()
        }

        assertEquals(100, percentages.last())
        assertTrue(percentages.size == 101) // 0..100
    }

    @Test
    fun testZeroElements() {
        val elements = 0
        val percentages = mutableListOf<Int>()
        val tracker = ProgressTracker(elements) { percent ->
            percentages.add(percent)
        }

        tracker.incIterate()

        assertTrue(percentages.isEmpty())
    }

    @Test
    fun testNegativeElements() {
        assertFails {
            val elements = -10
            ProgressTracker(elements) {}
        }
    }

    @Test
    fun testExcessiveIncrements() {
        val elements = 100
        val percentages = mutableListOf<Int>()
        val tracker = ProgressTracker(elements) { percent ->
            percentages.add(percent)
        }

        repeat(elements + 50) {
            tracker.incIterate()
        }

        assertEquals(100, percentages.last())
    }

    @Test
    fun testSingleElement() {
        val elements = 1
        val percentages = mutableListOf<Int>()
        val tracker = ProgressTracker(elements) { percent ->
            percentages.add(percent)
        }

        tracker.incIterate()

        assertEquals(listOf(100), percentages)
    }
}
