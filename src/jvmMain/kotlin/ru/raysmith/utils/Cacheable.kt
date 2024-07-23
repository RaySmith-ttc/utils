package ru.raysmith.utils

import kotlin.reflect.KProperty
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes
import kotlin.time.TimeSource

/**
 * A delegate that persists the value obtained from [getter] for [time] ms (5 minutes by default).
 * Updates the value after the time has elapsed
 * */
class Cacheable<T>(val time: Duration = 5.minutes, val getter: () -> T) {

    private var init = false
    private var cache: T? = null
    var lastRefreshTime = now()
        private set

    fun clear() {
        cache = null
    }

    fun refresh(): T {
        cache = getter()
        lastRefreshTime = now()
        init = true

        @Suppress("UNCHECKED_CAST")
        return cache as T
    }

    private fun now() = TimeSource.Monotonic.markNow()

    @Suppress("UNCHECKED_CAST")
    fun get() = if (!init || lastRefreshTime.plus(time) < now()) refresh() else cache as T

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return get()
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        cache = value
    }
}