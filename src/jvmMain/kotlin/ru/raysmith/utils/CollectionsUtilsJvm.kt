package ru.raysmith.utils


@Suppress("UNCHECKED_CAST")
fun <K, V> Map<out K?, V>.filterKeysNotNull(): Map<K, V> = filter { it.key != null } as Map<K, V>