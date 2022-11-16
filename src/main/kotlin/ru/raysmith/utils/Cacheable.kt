package ru.raysmith.utils

import kotlin.reflect.KProperty

/**
 * A delegate that persists the value obtained from [getter] for [time] ms (5 mutes by default).
 * Updates the value after the time has elapsed
 * */
class Cacheable<T>(val time: Long = 300_000, val getter: () -> T) {

    private var cache: T? = null
    private var lastTime = 0L

    fun clear() {
        cache = null
    }

    fun refresh(): T {
        cache = getter()
        lastTime = System.currentTimeMillis()

        @Suppress("UNCHECKED_CAST")
        return cache as T
    }

    fun get() = if (cache == null || lastTime + time < System.currentTimeMillis()) {
        cache = getter()
        lastTime = System.currentTimeMillis()

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