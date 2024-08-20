package me.kofesst.lovemood.ui

import androidx.compose.runtime.Composable
import me.kofesst.lovemood.app.LocalDictionary
import me.kofesst.lovemood.core.models.Gender
import me.kofesst.lovemood.core.text.AppTextHolder
import me.kofesst.lovemood.features.validation.operations.InvalidDateError
import me.kofesst.lovemood.features.validation.operations.InvalidMaskError
import me.kofesst.lovemood.features.validation.operations.LongValueError
import me.kofesst.lovemood.features.validation.operations.RequiredFieldError
import me.kofesst.lovemood.features.validation.operations.ShortValueError
import me.kofesst.lovemood.features.validation.operations.TooEarlyDateError
import me.kofesst.lovemood.features.validation.operations.TooLateDateError
import me.kofesst.lovemood.features.validation.operations.ValidationError
import me.kofesst.lovemood.localization.dictionary.ErrorsDictionary
import me.kofesst.lovemood.presentation.forms.InvalidFormValuesException
import me.kofesst.lovemood.presentation.screens.app.todos.AppTodo

val AppTodo.Status.uiString: String
    @Composable get() = with(LocalDictionary.current.todos) {
        when (this@uiString) {
            is AppTodo.Status.InDevelop -> inDevelopStatus.string()
            is AppTodo.Status.Released -> releasedStatus.string("%app_version%", appVersion)
        }
    }

val Throwable.uiText: AppTextHolder
    @Composable get() = this.uiText(LocalDictionary.current.errors)

fun Throwable.uiText(dictionary: ErrorsDictionary): AppTextHolder {
    return when (this) {
        is InvalidFormValuesException -> dictionary.invalidFormValues
        else -> dictionary.somethingWentWrong
    }
}

val ValidationError.uiText: AppTextHolder
    @Composable get() = this.uiText(LocalDictionary.current.errors)

fun ValidationError.uiText(dictionary: ErrorsDictionary): AppTextHolder {
    return when (this) {
        is RequiredFieldError -> dictionary.requiredField
        is ShortValueError -> dictionary.shortString
        is LongValueError -> dictionary.longString
        is InvalidMaskError -> dictionary.invalidStringMask

        is InvalidDateError -> dictionary.invalidDate
        is TooEarlyDateError -> dictionary.tooEarlyDate
        is TooLateDateError -> dictionary.tooLateDate

        else -> dictionary.somethingWentWrong
    }
}

val Gender.shortUiText: AppTextHolder
    @Composable get() = with(LocalDictionary.current.models) {
        when (this@shortUiText) {
            Gender.Male -> shortMaleGenderName
            Gender.Female -> shortFemaleGenderName
        }
    }