package ru.raysmith.utils

import kotlin.math.absoluteValue

private val cases = intArrayOf(2, 0, 1, 1, 1, 2)

/**
 * Возвращает вариант исчисления корректный для числа.
 *
 * ### Использование:
 *
 * Необходимо получить верное окончание для слова «фотография» во фрагменте предложения ... <i>n</i> фотограф(-ия/-ии/ий) ...
 *
 * val titles = listOf("фотография", "фотографии", "фотографий")
 *
 * Тогда будет возвращено:
 *
 * endWordForNum(1, titles) — фотография
 *
 * endWordForNum(3, titles) — фотографии
 *
 * endWordForNum(10, titles) — фотографий
 *
 * @param number число
 * @param titles список с 3-мя вариантами исчисления
 * */
fun endWordForNum(number: Number, titles: List<String>): String {
    val longNumber = number.toLong()
    require(titles.size == 3) { "Список исчислений должен иметь 3 варианта" }
    return titles[
            if (longNumber % 100 in 5..19) 2
            else cases[(
                    if (longNumber % 10 < 5) longNumber % 10
                    else 5).absoluteValue.toInt()
            ]
    ]
}

/**
 * Содержит варианты расположения числа
 * */
enum class EndWordForNumNumberSide {
    LEFT, RIGHT
}

/**
 * Дополнение к функции [endWordForNum].
 *
 * @param number число
 * @param titles список с 3-мя вариантами исчисления
 * @param numberSide сторона, с которой нужно разместить число
 *
 * @return вариант исчисления вместе с числом.
 * */
fun endWordForNumWithNumber(
    number: Number,
    titles: List<String>,
    numberSide: EndWordForNumNumberSide = EndWordForNumNumberSide.LEFT
): String {
    return when(numberSide) {
        EndWordForNumNumberSide.LEFT -> "$number ${endWordForNum(number, titles)}"
        EndWordForNumNumberSide.RIGHT -> "${endWordForNum(number, titles)} $number"
    }
}