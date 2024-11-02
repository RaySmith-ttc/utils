package ru.raysmith.utils

/**
 * A utility class to track and notify progress percentage based on iterations over a set number of elements.
 *
 * @param elements The total number of elements to process. Must be positive.
 * @param onPercent Callback function to be invoked when the percentage increases.
 */
class ProgressTracker(
    val elements: Int,
    val onPercent: (percent: Int) -> Unit
) {
    // The current count of iterations completed.
    private var iterates = 0

    // The last percentage value that was notified.
    private var lastPercent = -1

    init {
        require(elements >= 0) { "elements must be positive" }
    }

    /**
     * Increments the iteration count by one and checks if the percentage completed has increased.
     * If so, invokes the [onPercent] callback with the new percentage.
     */
    fun incIterate() {
        if (elements == 0) return
        val percent = ((++iterates).coerceAtMost(elements) / elements.toDouble() * 100).toInt()
        if (percent > lastPercent) {
            onPercent(percent)
            lastPercent = percent
        }
    }
}