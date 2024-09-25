package me.kofesst.lovemood.localization

import me.kofesst.lovemood.localization.screens.ScreensLocalization
import me.kofesst.lovemood.localization.text.DatePeriodTextBuilder
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Локализация приложения.
 */
@Singleton
class AppLocalization @Inject constructor(
    val errors: ErrorsLocalization,
    val commonWords: CommonWordsLocalization,
    val cases: CasesLocalization,
    val abbreviates: AbbreviatesLocalization,
    val screens: ScreensLocalization
) {
    /**
     * Построитель текста периода дат.
     */
    val datePeriod: DatePeriodTextBuilder = DatePeriodTextBuilder().apply {
        yearCasesBuilder = cases.yearCases
        monthCasesBuilder = cases.monthCases
        dayCasesBuilder = cases.dayCases
    }
}