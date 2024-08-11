package me.kofesst.lovemood.core.text

/**
 * Хранилище текста, который может изменяться в зависимости от количества.
 *
 * [firstCase] - хранилище текста, если последняя цифра количества равна 1
 * (но последние две последние цифры не лежат в промежутке от 11 до 19).
 *
 * [secondCase] - хранилище текста, если последняя цифра количества лежит в промежутке от 2 до 4
 * (но последние две последние цифры не лежат в промежутке от 11 до 19).
 *
 * [thirdCase] - хранилище текста, если последняя цифра количества лежит в промежутке от 5 до 9,
 * а также если две последние цифры лежат в промежутке от 11 до 19).
 */
data class TextCasesHolder(
    val firstCase: AppTextHolder,
    val secondCase: AppTextHolder,
    val thirdCase: AppTextHolder
) : AppTextHolder {
    override fun string(vararg args: Any): String {
        if (args.size !in 1..2) return "invalid"
        val amount = args[0].toString().toIntOrNull() ?: return "invalid"
        val appendAmount = if (args.size != 2) true else args[1].toString().toBoolean()
        val remainder = amount % 10
        val textCase = when {
            amount % 100 in 11..19 || remainder in 5..9 -> thirdCase
            remainder == 1 -> firstCase
            remainder in 2..4 -> secondCase
            else -> thirdCase
        }.string()
        return if (appendAmount) "$amount $textCase"
        else textCase
    }
}