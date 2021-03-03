package ru.raysmith.utils

private val cases = intArrayOf(2, 0, 1, 1, 1, 2)

/**
 * Возвращает вариант исчесления корректный для числа.
 *
 * ### Использование:
 *
 * Необходимо получить верное окончание для слова «фотография» в фрагменте предложения ... <i>n</i> фотограф(-ия/-ии/ий) ...
 *
 * val titles = listOf("фотография", "фотографии", "фотографий")
 *
 * Тогда будет возварщено:
 *
 * endWordForNum(1, titles) — фотография
 *
 * endWordForNum(3, titles) — фотографии
 *
 * endWordForNum(10, titles) — фотографий
 *
 * @param number число
 * @param titles список с 3-мя вариантами исчесления
 * */
fun endWordForNum(number: Int, titles: List<String>): String {
    require(titles.size == 3) { "Список исчеслений должен иметь 3 варианта" }
    return titles[
            if (number % 100 in 5..19) 2
            else cases[
                    if (number % 10 < 5) number % 10
                    else 5
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
 * @param titles список с 3-мя вариантами исчесления
 * @param numberSide сторона, с которой нужно разместить число
 *
 * @return вариант исчесления вместе с числом.
 * */
fun endWordForNumWithNumber(number: Int, titles: List<String>, numberSide: EndWordForNumNumberSide = EndWordForNumNumberSide.LEFT): String {
    return when(numberSide) {
        EndWordForNumNumberSide.LEFT -> "$number ${endWordForNum(number, titles)}"
        EndWordForNumNumberSide.RIGHT -> "${endWordForNum(number, titles)} $number"
    }
}