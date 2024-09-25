package me.kofesst.lovemood.localization

import me.kofesst.lovemood.localization.text.NumericTextBuilder
import me.kofesst.lovemood.localization.text.TextBuilder
import me.kofesst.lovemood.localization.text.TextBuilderFormat
import me.kofesst.lovemood.localization.text.TextCasesBuilder
import org.junit.Test

class TextBuilderTests {
    private val textCasesBuilder = TextCasesBuilder().apply {
        firstCase = TextBuilder.fromString("first")
        secondCase = TextBuilder.fromString("second")
        thirdCase = TextBuilder.fromString("third")
    }
    private val expectedTextCases: Map<Int, String> = mapOf(
        1 to "first",
        2 to "second",
        5 to "third",
        11 to "third",
        15 to "third",
        119 to "third",
        121 to "first",
        1023 to "second",
        1025 to "third",
    )

    @Test
    fun ensureTextCasesWorksAsExpected() {
        expectedTextCases.forEach { (amount, expectedCase) ->
            val actualCase = textCasesBuilder
                .apply { this.amount = amount }
                .build()
            assert(expectedCase == actualCase) {
                "Cases are different: [amount=$amount, expected=$expectedCase, actual=$actualCase]"
            }
        }
    }

    private val numericTextBuilder = NumericTextBuilder().apply {
        addAbbreviate(TextBuilder.fromString("тыс."), 1_000)
        addAbbreviate(TextBuilder.fromString("млрд."), 1_000_000_000)
        format = TextBuilderFormat.ArgumentsBeforeText()
        fractionDigitCount = 2
    }
    private val expectedNumericAbbreviates: Map<Long, String> = mapOf(
        1_500 to "1,5 тыс.",
        1_567_899 to "1567,9 тыс.",
        1_567_890_987 to "1,57 млрд."
    )

    @Test
    fun ensureNumericTextWorksAsExpected() {
        expectedNumericAbbreviates.forEach { (number, expectedAbbreviate) ->
            val actualAbbreviate = numericTextBuilder
                .apply { this.number = number }
                .build()
            assert(expectedAbbreviate == actualAbbreviate) {
                "Abbreviates are different: [number=$number, expected=$expectedAbbreviate, actual=$actualAbbreviate]"
            }
        }
    }
}