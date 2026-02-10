package ru.raysmith.utils

import java.util.Locale

/**
 * Capitalizes the first character of this string using the specified [locale] (or the default locale if not specified)
 * */
fun String.capitalize(locale: Locale = Locale.getDefault()) = this.replaceFirstChar {
    if (it.isLowerCase()) it.titlecase(locale) else it.toString()
}
