package me.kofesst.lovemood.localization.text

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import me.kofesst.lovemood.features.date.DatePeriod

/**
 * Построитель текста периода дат.
 */
class DatePeriodTextBuilder : TextBuilder() {
    /**
     * Построитель текста в случае, если период пустой.
     */
    var emptyPeriodTextBuilder: TextBuilder = Empty

    /**
     * Склонения слова "Год".
     */
    var yearCasesBuilder: TextCasesBuilder = TextCasesBuilder()

    /**
     * Склонения слова "Месяц".
     */
    var monthCasesBuilder: TextCasesBuilder = TextCasesBuilder()

    /**
     * Склонения слова "День".
     */
    var dayCasesBuilder: TextCasesBuilder = TextCasesBuilder()

    /**
     * Период.
     */
    var period: DatePeriod = DatePeriod.Empty

    override fun buildAnnotated(
        textStyle: SpanStyle,
        argumentsStyle: SpanStyle
    ): AnnotatedString = buildAnnotatedString {
        val units: Map<TextCasesBuilder, Int> = mapOf(
            yearCasesBuilder to period.years,
            monthCasesBuilder to period.months,
            dayCasesBuilder to period.days
        )
            .filterValues { it > 0 }
            .mapKeys { (builder, unit) ->
                builder.apply {
                    format = TextBuilderFormat.OnlyText
                    amount = unit
                }
            }
        if (units.isEmpty()) {
            append(
                emptyPeriodTextBuilder.buildAnnotated(textStyle, argumentsStyle)
            )
            return@buildAnnotatedString
        }
        units.keys.forEachIndexed { index, unitBuilder ->
            val unit = units[unitBuilder] ?: -1
            withStyle(argumentsStyle) {
                append(unit.toString())
            }
            withStyle(textStyle) {
                append(" ")
                append(unitBuilder.build())
                if (index != units.size - 1) append(" ")
            }
        }
    }

    override fun prepareText(): Pair<String, List<Any>> {
        val text = buildString {
            val units: Map<TextCasesBuilder, Int> = mapOf(
                yearCasesBuilder to period.years,
                monthCasesBuilder to period.months,
                dayCasesBuilder to period.days
            )
                .filterValues { it > 0 }
                .mapKeys { (builder, _) -> builder.apply { format = TextBuilderFormat.OnlyText } }
            if (units.isEmpty()) {
                append(
                    emptyPeriodTextBuilder.build()
                )
                return@buildString
            }
            units.keys.forEachIndexed { index, unitBuilder ->
                val unit = units[unitBuilder] ?: -1
                append(unit.toString())
                append(" ")
                append(unitBuilder.build())
                if (index != units.size - 1) append(" ")
            }
        }
        return text to emptyList()
    }
}