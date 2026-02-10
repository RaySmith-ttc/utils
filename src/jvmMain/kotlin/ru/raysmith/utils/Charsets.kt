package ru.raysmith.utils

import java.nio.charset.Charset

/** Charset for Windows-1251 encoding */
@Suppress("UnusedReceiverParameter")
val Charsets.WINDOWS_1251: Charset get() = Charset.forName("windows-1251")
