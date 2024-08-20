package me.kofesst.lovemood.localization.dictionary

import android.content.Context
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import me.kofesst.lovemood.core.text.AppTextHolder
import me.kofesst.lovemood.core.text.TextCasesHolder
import me.kofesst.lovemood.features.date.DatePeriod
import me.kofesst.lovemood.localization.R
import me.kofesst.lovemood.localization.ResourceText

class DatesDictionary(appContext: Context) {
    private val daysFirstCase = ResourceText(
        R.string.dates__days_first_case, appContext
    )
    private val daysSecondCase = ResourceText(
        R.string.dates__days_second_case, appContext
    )
    private val daysThirdCase = ResourceText(
        R.string.dates__days_third_case, appContext
    )
    val daysTextCases = TextCasesHolder(
        firstCase = daysFirstCase,
        secondCase = daysSecondCase,
        thirdCase = daysThirdCase
    )

    private val monthsFirstCase = ResourceText(
        R.string.dates__months_first_case, appContext
    )
    private val monthsSecondCase = ResourceText(
        R.string.dates__months_second_case, appContext
    )
    private val monthsThirdCase = ResourceText(
        R.string.dates__months_third_case, appContext
    )
    val monthsTextCases = TextCasesHolder(
        firstCase = monthsFirstCase,
        secondCase = monthsSecondCase,
        thirdCase = monthsThirdCase
    )

    private val yearsFirstCase = ResourceText(
        R.string.dates__years_first_case, appContext
    )
    private val yearsSecondCase = ResourceText(
        R.string.dates__years_second_case, appContext
    )
    private val yearsThirdCase = ResourceText(
        R.string.dates__years_third_case, appContext
    )
    val yearsTextCases = TextCasesHolder(
        firstCase = yearsFirstCase,
        secondCase = yearsSecondCase,
        thirdCase = yearsThirdCase
    )

    fun buildAnnotatedPeriodString(
        period: DatePeriod,
        ifPeriodEmpty: String = "",
        unitSpanStyle: SpanStyle = SpanStyle(fontWeight = FontWeight.Bold),
        unitNameSpanStyle: SpanStyle = SpanStyle(),
        unitsDelimiter: String = " "
    ): AnnotatedString = buildAnnotatedString {
        val units = mapOf(
            yearsTextCases to period.years,
            monthsTextCases to period.months,
            daysTextCases to period.days
        ).filter { it.value > 0 }
        if (units.isEmpty()) {
            append(ifPeriodEmpty)
        }
        units.keys.forEachIndexed { index, unitNameHolder ->
            val unit = units[unitNameHolder] ?: -1
            appendPeriodUnit(unit, unitNameHolder, unitSpanStyle, unitNameSpanStyle)
            if (index != units.size - 1) append(unitsDelimiter)
        }
    }

    private fun AnnotatedString.Builder.appendPeriodUnit(
        unit: Int,
        unitNameHolder: AppTextHolder,
        unitSpanStyle: SpanStyle,
        unitNameSpanStyle: SpanStyle
    ) {
        withStyle(unitSpanStyle) {
            append(unit.toString())
        }
        append(" ")
        withStyle(unitNameSpanStyle) {
            append(unitNameHolder.string(unit, false))
        }
    }
}