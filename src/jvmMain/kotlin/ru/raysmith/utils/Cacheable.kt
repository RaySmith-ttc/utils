package ru.raysmith.utils

import kotlin.reflect.KProperty
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes
import kotlin.time.TimeSource

/**
 * A delegate that persists the value obtained from [getter] for [time] ms (infinite by default).
 * Updates the value after access if the time has passed since the last update.
 *
 * @param minTime if passed and not equal to zero, the value will not be updated if the last update was less than
 *  [minTime] ago. This is useful when the value may be updated too often with [refresh]
 * */
class Cacheable<T>(val time: Duration = Duration.INFINITE, val minTime: Duration = Duration.ZERO, val getter: () -> T) {

    private var init = false
    private var cache: T? = null

    /** The time of the last update. Updated on each update and reset. */
    var lastRefreshTime = now()
        private set

    /** Resets the state to init. Cached value will be lost, [lastRefreshTime] not be updated. */
    fun reset() {
        init = false
        cache = null
    }

    /** Updates the cache with the value obtained from [getter] and returns it. Also updates [lastRefreshTime] to the current time. */
    @Suppress("UNCHECKED_CAST")
    fun refresh(): T {
        if (init && minTime != Duration.ZERO && lastRefreshTime > now() - minTime) {
            return cache as T
        }
        cache = getter()
        lastRefreshTime = now()
        init = true

        return cache as T
    }

    private fun now() = TimeSource.Monotonic.markNow()

    /**
     * Returns the cached value if it is initialized and the time has not passed since the last update,
     * otherwise updates the cache and returns the new value.
     * */
    @Suppress("UNCHECKED_CAST")
    fun get(forceCache: Boolean = false): T {
        if (init && forceCache) return cache as T

        return if (!init || lastRefreshTime.plus(time) < now()) refresh() else cache as T
    }

    /** Sets the value of the cache to [value] and updates [lastRefreshTime] to the current time. */
    fun set(value: T) {
        cache = value
        init = true
        lastRefreshTime = now()
    }

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T = get()
    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T): Unit = set(value)
}
