package ru.raysmith.utils

import kotlin.reflect.KProperty
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds
import kotlin.time.TimeSource

/**
 * A delegate that persists the value obtained from [getter] for [time] ms (5 minutes by default).
 * Updates the value after the time has elapsed
 *
 * @param minTime if passed
 * */
// TODO impl minTime
class Cacheable<T>(val time: Duration = 5.minutes, val minTime: Duration = Duration.ZERO, val getter: () -> T) {

    private var init = false
    private var cache: T? = null
    var lastRefreshTime = now()
        private set

    fun clear() {
        cache = null
    }

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

    @Suppress("UNCHECKED_CAST")
    fun get(forceCache: Boolean = false): T {
        if (init && forceCache) return cache as T

        return if (!init || lastRefreshTime.plus(time) < now()) refresh() else cache as T
    }

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return get()
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        cache = value
    }
}