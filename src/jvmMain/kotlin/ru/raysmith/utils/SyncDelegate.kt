package ru.raysmith.utils

import kotlin.properties.Delegates
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/** Creates a thread-safe delegate that synchronizes access to the property value. */
@Suppress("UnusedReceiverParameter")
fun <T> Delegates.sync(defaultValue: T) = SyncDelegate(defaultValue)

/** Thread-safe delegate that synchronizes access to the property value. */
class SyncDelegate<T>(defaultValue: T): ReadWriteProperty<Any, T> {
    val lock = Any()

    private var currentValue = defaultValue

    override fun getValue(thisRef: Any, property: KProperty<*>): T = synchronized(lock) {
        currentValue
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) = synchronized(lock) {
        currentValue = value
    }
}
