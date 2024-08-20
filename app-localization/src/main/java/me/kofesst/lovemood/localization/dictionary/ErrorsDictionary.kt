package me.kofesst.lovemood.localization.dictionary

import android.content.Context
import me.kofesst.lovemood.localization.R
import me.kofesst.lovemood.localization.ResourceText

@Suppress("MemberVisibilityCanBePrivate")
class ErrorsDictionary(appContext: Context) {
    val somethingWentWrong = ResourceText(
        R.string.errors__something_went_wrong, appContext
    )

    val requiredField = ResourceText(
        R.string.errors__required_field, appContext
    )
    val shortString = ResourceText(
        R.string.errors__short_string, appContext
    )
    val longString = ResourceText(
        R.string.errors__long_string, appContext
    )
    val invalidStringMask = ResourceText(
        R.string.errors__invalid_string_mask, appContext
    )

    val invalidDate = ResourceText(
        R.string.errors__invalid__date, appContext
    )
    val tooEarlyDate = ResourceText(
        R.string.errors__too_early_date, appContext
    )
    val tooLateDate = ResourceText(
        R.string.errors__too_late_date, appContext
    )

    val invalidFormValues = ResourceText(
        R.string.errors__invalid_form_values, appContext
    )
}