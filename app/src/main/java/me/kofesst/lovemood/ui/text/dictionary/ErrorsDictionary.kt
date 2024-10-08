package me.kofesst.lovemood.ui.text.dictionary

import android.content.Context
import me.kofesst.lovemood.R
import me.kofesst.lovemood.core.text.AppTextHolder
import me.kofesst.lovemood.features.validation.operations.InvalidDateError
import me.kofesst.lovemood.features.validation.operations.InvalidMaskError
import me.kofesst.lovemood.features.validation.operations.LongValueError
import me.kofesst.lovemood.features.validation.operations.RequiredFieldError
import me.kofesst.lovemood.features.validation.operations.ShortValueError
import me.kofesst.lovemood.features.validation.operations.TooEarlyDateError
import me.kofesst.lovemood.features.validation.operations.TooLateDateError
import me.kofesst.lovemood.features.validation.operations.ValidationError
import me.kofesst.lovemood.presentation.forms.InvalidFormValuesException
import me.kofesst.lovemood.ui.text.ResourceText

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

    fun fromError(error: Throwable): AppTextHolder {
        return when (error) {
            is InvalidFormValuesException -> invalidFormValues
            else -> somethingWentWrong
        }
    }

    fun fromValidationError(error: ValidationError): AppTextHolder {
        return when (error) {
            is RequiredFieldError -> requiredField
            is ShortValueError -> shortString
            is LongValueError -> longString
            is InvalidMaskError -> invalidStringMask

            is InvalidDateError -> invalidDate
            is TooEarlyDateError -> tooEarlyDate
            is TooLateDateError -> tooLateDate

            else -> somethingWentWrong
        }
    }
}

fun ValidationError.textHolder(dictionary: ErrorsDictionary) =
    dictionary.fromValidationError(this)