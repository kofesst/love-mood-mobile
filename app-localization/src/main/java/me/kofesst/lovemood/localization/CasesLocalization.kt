package me.kofesst.lovemood.localization

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import me.kofesst.lovemood.localization.text.TextCasesBuilder
import me.kofesst.lovemood.localization.text.buildResource
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Локализация склонений слов.
 */
@Singleton
class CasesLocalization @Inject constructor(
    @ApplicationContext context: Context
) {
    /**
     * Склонения слова "День".
     */
    val dayCases = TextCasesBuilder().apply {
        firstCase = context buildResource R.string.cases__days_first_case
        secondCase = context buildResource R.string.cases__days_second_case
        thirdCase = context buildResource R.string.cases__days_third_case
    }

    /**
     * Склонения слова "Месяц".
     */
    val monthCases = TextCasesBuilder().apply {
        firstCase = context buildResource R.string.cases__months_first_case
        secondCase = context buildResource R.string.cases__months_second_case
        thirdCase = context buildResource R.string.cases__months_third_case
    }

    /**
     * Склонения слова "Год".
     */
    val yearCases = TextCasesBuilder().apply {
        firstCase = context buildResource R.string.cases__years_first_case
        secondCase = context buildResource R.string.cases__years_second_case
        thirdCase = context buildResource R.string.cases__years_third_case
    }
}