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

/** Append a [symbol] to start and end of [this] string */
fun String.wrap(symbol: Char) = "$symbol$this$symbol"

/**
 * Returns a string containing the first [n] characters from this string + 3-bytes character `…`,
 * or the entire string if the string is shorter.
 *
 * Example:
 * ```
 * "Hello world".takeOrCut(6) // "Hello…"
 * "Hello world".takeOrCut(5, countDots = false) // "Hello…"
 * ```
 *
 * @param countDots if true and string longer than [n], result string's length with included `…` will be [n] length,
 * otherwise [n] length + 1
 * @throws IllegalArgumentException if [n] is negative.
 * */
fun String.takeOrCut(n: Int, countDots: Boolean = true) = when {
    n == 0 && countDots -> ""
    n == 0 -> "…"
    else -> letIf(length > n) {
        "${it.take(if (countDots) n - 1 else n)}…"
    }
}
