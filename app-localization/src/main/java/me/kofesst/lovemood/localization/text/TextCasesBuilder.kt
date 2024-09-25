package me.kofesst.lovemood.localization.text

/**
 * Текст, который может изменяться в зависимости от количества.
 */
class TextCasesBuilder : TextBuilder() {
    /**
     * Вариант текста, если последняя цифра количества равна 1,
     * но последние две последние цифры не лежат в промежутке от 11 до 19.
     */
    var firstCase: TextBuilder = Empty

    /**
     * Вариант текста, если последняя цифра количества лежит в промежутке от 2 до 4,
     * но последние две последние цифры не лежат в промежутке от 11 до 19.
     */
    var secondCase: TextBuilder = Empty

    /**
     * Вариант текста, если последняя цифра количества лежит в промежутке от 5 до 9,
     * а также если две последние цифры лежат в промежутке от 11 до 19).
     */
    var thirdCase: TextBuilder = Empty

    /**
     * Количество, относительно которого будет выбран вариант текста.
     */
    var amount: Int = 0

    override fun prepareText(): Pair<String, List<Any>> {
        val reminder = amount % 10
        val case = when {
            amount % 100 in 11..19 -> thirdCase
            reminder == 1 -> firstCase
            reminder in 2..4 -> secondCase
            else -> thirdCase
        }
        return case.build() to listOf(amount)
    }
}