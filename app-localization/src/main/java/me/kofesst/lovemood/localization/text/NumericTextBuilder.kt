package me.kofesst.lovemood.localization.text

import androidx.compose.ui.util.fastFilter
import java.text.DecimalFormat

/**
 * Сокращение числа.
 *
 * [textBuilder] - построитель текста сокращения.
 *
 * [multiplicity] - кратность числа.
 *
 * Например:
 *
 * ```
 * val thousandMeasure = NumericTextAbbreviate(
 *     multiplicity = 1_000,
 *     text = "тыс."
 * )
 *
 * val millionMeasure = NumericTextAbbreviate(
 *     multiplicity = 1_000_000,
 *     text = "млн."
 * )
 * ```
 */
class NumericAbbreviate(
    val textBuilder: TextBuilder,
    val multiplicity: Long
) : Comparable<NumericAbbreviate> {
    /**
     * Сравнивает две аббревиатуры между собой относительно кратности числа.
     */
    override fun compareTo(other: NumericAbbreviate): Int {
        return multiplicity.compareTo(other.multiplicity)
    }
}

/**
 * Создает [NumericTextBuilder] из построителя текста и кратности числа [multiplicity].
 */
infix fun TextBuilder.abbreviates(multiplicity: Long): NumericAbbreviate {
    return NumericAbbreviate(
        textBuilder = this,
        multiplicity = multiplicity
    )
}

/**
 * Построитель текста, который зависит от размера числа.
 *
 * Например: "100 млн.", "50 млрд.", "1 тыс.".
 *
 * Аббревиатура числа выбирается по принципу наименьшего числа в тексте. Например,
 * передан список аббревиатур: `["тыс.", "млн.", "млрд."]`, и число при форматировании - `9_876_543`.
 * Будет выбрана аббревиатура `"млн."`, а исходный текст - `"9.9 млн."`.
 */
class NumericTextBuilder : TextBuilder() {
    /**
     * Список аббревиатур.
     */
    var abbreviates: MutableList<NumericAbbreviate> = mutableListOf()

    /**
     * Количество цифр в остатке от целого числа.
     */
    var fractionDigitCount: Int = 0

    /**
     * Число, относительно которого будет строиться текст.
     */
    var number: Long = 0

    /**
     * Добавляет аббревиатуру, используя её параметры.
     *
     * [textBuilder] - построитель текста аббревиатуры.
     *
     * [multiplicity] - кратность числа.
     */
    fun addAbbreviate(textBuilder: TextBuilder, multiplicity: Long) {
        addAbbreviate(
            NumericAbbreviate(
                textBuilder = textBuilder,
                multiplicity = multiplicity
            )
        )
    }

    /**
     * Добавляет аббревиатуру [abbreviate].
     */
    fun addAbbreviate(abbreviate: NumericAbbreviate) {
        abbreviates += abbreviate
    }

    private fun getDecimalFormat(): DecimalFormat {
        return DecimalFormat().apply {
            minimumFractionDigits = 0
            maximumFractionDigits = fractionDigitCount
            isGroupingUsed = false
        }
    }

    override fun prepareText(): Pair<String, List<Any>> {
        return abbreviates
            .fastFilter { it.multiplicity <= number }
            .sortedDescending()
            .firstOrNull()?.let { suitableAbbreviate ->
                val decimalFormat = getDecimalFormat()
                val decimalNumber = number.toDouble() / suitableAbbreviate.multiplicity
                val text = suitableAbbreviate.textBuilder.build()
                text to listOf(decimalFormat.format(decimalNumber))
            } ?: ("invalid" to listOf(number))
    }
}