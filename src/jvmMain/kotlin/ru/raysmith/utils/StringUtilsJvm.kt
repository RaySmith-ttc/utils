package ru.raysmith.utils

import java.util.Locale

fun String.capitalize(locale: Locale = Locale.getDefault()) = this.replaceFirstChar {
    if (it.isLowerCase()) it.titlecase(locale) else it.toString()
}