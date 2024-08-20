package me.kofesst.lovemood.localization.dictionary

import android.content.Context
import me.kofesst.lovemood.core.text.TextCasesHolder
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
}