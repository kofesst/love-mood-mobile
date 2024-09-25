package me.kofesst.lovemood.localization

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import me.kofesst.lovemood.features.validation.operations.InvalidMaskError
import me.kofesst.lovemood.features.validation.operations.LongValueError
import me.kofesst.lovemood.features.validation.operations.RequiredFieldError
import me.kofesst.lovemood.features.validation.operations.ShortValueError
import me.kofesst.lovemood.features.validation.operations.TooEarlyDateError
import me.kofesst.lovemood.features.validation.operations.TooLateDateError
import me.kofesst.lovemood.features.validation.operations.ValidationError
import me.kofesst.lovemood.localization.text.TextBuilder
import me.kofesst.lovemood.localization.text.buildResource
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Локализация ошибок приложения.
 */
@Singleton
class ErrorsLocalization @Inject constructor(
    @ApplicationContext context: Context
) {
    val somethingWentWrong = context buildResource R.string.errors__something_went_wrong
    val requiredField = context buildResource R.string.errors__required_field
    val shortString = context buildResource R.string.errors__short_string
    val longString = context buildResource R.string.errors__long_string
    val invalidStringMask = context buildResource R.string.errors__invalid_string_mask
    val tooEarlyDate = context buildResource R.string.errors__too_early_date
    val tooLateDate = context buildResource R.string.errors__too_late_date

    fun fromValidationError(error: ValidationError): TextBuilder {
        return when (error) {
            is RequiredFieldError -> requiredField
            is ShortValueError -> shortString
            is LongValueError -> longString
            is InvalidMaskError -> invalidStringMask
            is TooEarlyDateError -> tooEarlyDate
            is TooLateDateError -> tooLateDate
            else -> somethingWentWrong
        }
    }
}