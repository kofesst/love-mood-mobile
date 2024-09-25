package me.kofesst.lovemood.localization

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import me.kofesst.lovemood.localization.text.TextCasesBuilder
import me.kofesst.lovemood.localization.text.buildResource
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Локализация аббревиатур чисел.
 */
@Singleton
class AbbreviatesLocalization @Inject constructor(
    @ApplicationContext context: Context
) {
    /**
     * Аббревиатура тысячи.
     */
    val thousandAbbreviate = TextCasesBuilder().apply {
        firstCase = context buildResource R.string.abbreviates__thousand_first_case
        secondCase = context buildResource R.string.abbreviates__thousand_second_case
        thirdCase = context buildResource R.string.abbreviates__thousand_third_case
    }

    /**
     * Аббревиатура миллиона.
     */
    val millionAbbreviate = TextCasesBuilder().apply {
        firstCase = context buildResource R.string.abbreviates__million_first_case
        secondCase = context buildResource R.string.abbreviates__million_second_case
        thirdCase = context buildResource R.string.abbreviates__million_third_case
    }

    /**
     * Аббревиатура миллиарда.
     */
    val billionAbbreviate = TextCasesBuilder().apply {
        firstCase = context buildResource R.string.abbreviates__billion_first_case
        secondCase = context buildResource R.string.abbreviates__billion_second_case
        thirdCase = context buildResource R.string.abbreviates__billion_third_case
    }

    /**
     * Аббревиатура триллиона.
     */
    val trillionAbbreviate = TextCasesBuilder().apply {
        firstCase = context buildResource R.string.abbreviates__trillion_first_case
        secondCase = context buildResource R.string.abbreviates__trillion_second_case
        thirdCase = context buildResource R.string.abbreviates__trillion_third_case
    }

    /**
     * Аббревиатура тысячи в винительном падеже.
     */
    val thousandAbbreviateAcc = TextCasesBuilder().apply {
        firstCase = context buildResource R.string.abbreviates__thousand_first_case_acc
        secondCase = context buildResource R.string.abbreviates__thousand_second_case_acc
        thirdCase = context buildResource R.string.abbreviates__thousand_third_case_acc
    }

    /**
     * Аббревиатура миллиона в винительном падеже.
     */
    val millionAbbreviateAcc = TextCasesBuilder().apply {
        firstCase = context buildResource R.string.abbreviates__million_first_case_acc
        secondCase = context buildResource R.string.abbreviates__million_second_case_acc
        thirdCase = context buildResource R.string.abbreviates__million_third_case_acc
    }

    /**
     * Аббревиатура миллиарда в винительном падеже.
     */
    val billionAbbreviateAcc = TextCasesBuilder().apply {
        firstCase = context buildResource R.string.abbreviates__billion_first_case_acc
        secondCase = context buildResource R.string.abbreviates__billion_second_case_acc
        thirdCase = context buildResource R.string.abbreviates__billion_third_case_acc
    }

    /**
     * Аббревиатура триллиона в винительном падеже.
     */
    val trillionAbbreviateAcc = TextCasesBuilder().apply {
        firstCase = context buildResource R.string.abbreviates__trillion_first_case_acc
        secondCase = context buildResource R.string.abbreviates__trillion_second_case_acc
        thirdCase = context buildResource R.string.abbreviates__trillion_third_case_acc
    }
}