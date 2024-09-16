package ru.raysmith.utils

import java.util.*

actual fun uuid(zero: Boolean) = if (zero) "00000000-0000-0000-0000-000000000000" else UUID.randomUUID().toString()