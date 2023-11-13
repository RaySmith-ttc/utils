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

    private var cache: T? = null
    private var lastTime = TimeSource.Monotonic.markNow()

    fun clear() {
        cache = null
    }

    fun refresh(): T {
        cache = getter()
        lastTime = TimeSource.Monotonic.markNow()

        @Suppress("UNCHECKED_CAST")
        return cache as T
    }

    fun get() = if (cache == null || lastTime.plus(time) < TimeSource.Monotonic.markNow()) {
        cache = getter()
        lastTime = TimeSource.Monotonic.markNow()

        @Suppress("UNCHECKED_CAST")
        cache as T
    } else cache!!

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return get()
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        cache = value
    }
}