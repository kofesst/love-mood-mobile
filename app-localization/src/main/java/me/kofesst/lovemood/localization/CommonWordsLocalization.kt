package me.kofesst.lovemood.localization

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import me.kofesst.lovemood.localization.text.TextCasesBuilder
import me.kofesst.lovemood.localization.text.buildResource
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Локализация обычных слов, использующихся в приложении.
 */
@Singleton
class CommonWordsLocalization @Inject constructor(
    @ApplicationContext context: Context
) {
    /**
     * Склонения слова "Литр".
     */
    val litersCases = TextCasesBuilder().apply {
        firstCase = context buildResource R.string.cases__liters_first_case
        secondCase = context buildResource R.string.cases__liters_second_case
        thirdCase = context buildResource R.string.cases__liters_third_case
    }

    /**
     * Склонения слова "Раз".
     */
    val timesCases = TextCasesBuilder().apply {
        firstCase = context buildResource R.string.cases__times_first_case
        secondCase = context buildResource R.string.cases__times_second_case
        thirdCase = context buildResource R.string.cases__times_third_case
    }

    /**
     * Склонения слова "Звезда".
     */
    val starsCases = TextCasesBuilder().apply {
        firstCase = context buildResource R.string.cases__stars_first_case
        secondCase = context buildResource R.string.cases__stars_second_case
        thirdCase = context buildResource R.string.cases__stars_third_case
    }

    /**
     * Склонения слова "Человек".
     */
    val peopleCases = TextCasesBuilder().apply {
        firstCase = context buildResource R.string.cases__people_first_case
        secondCase = context buildResource R.string.cases__people_second_case
        thirdCase = context buildResource R.string.cases__people_third_case
    }
}