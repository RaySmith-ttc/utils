package ru.raysmith.utils

private val symbols = (('A'..'Z') + ('a'..'z') + ('0'..'9'))

/**
 * Генерирует случайную строку заданной длины, используя указанные символы.
 * Если символы не предоставлены, используются символы по умолчанию (заглавные буквы, строчные буквы и цифры).
 *
 * @param length длина генерируемой строки
 * @param symbols список символов, которые будут использоваться для генерации строки
 */
fun generateRandomString(length: Int, symbols: List<Char> = ru.raysmith.utils.symbols) = buildString {
    repeat(length) {
        append(symbols.random())
    }
}