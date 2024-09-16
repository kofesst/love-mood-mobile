package me.kofesst.lovemood.presentation.forms.profile

import me.kofesst.lovemood.core.models.Profile
import me.kofesst.lovemood.features.date.DateTimePattern
import me.kofesst.lovemood.features.validation.operations.DateValidation
import me.kofesst.lovemood.features.validation.operations.StringValidation
import me.kofesst.lovemood.features.validation.operations.consumeOperations
import me.kofesst.lovemood.localization.dictionary.AppDictionary
import me.kofesst.lovemood.presentation.forms.FormValidator
import me.kofesst.lovemood.ui.uiText
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Валидатор формы профиля.
 */
@Singleton
class ProfileFormValidator @Inject constructor(
    private val dateTimePattern: DateTimePattern,
    private val dictionary: AppDictionary
) : FormValidator<Profile, ProfileFormState> {
    companion object {
        val USERNAME_LENGTH_RANGE = 2..48
        val MIN_DATE_OF_BIRTH: LocalDate = LocalDate.of(1900, 1, 1)
        val MAX_DATE_OF_BRITH: LocalDate = LocalDate.now()
    }

    override fun validate(form: ProfileFormState): ProfileFormState {
        val usernameError = consumeOperations(
            StringValidation.required,
            StringValidation.length(USERNAME_LENGTH_RANGE)
        ).validate(form.username)
        val dateOfBirthError = consumeOperations(
            DateValidation.cast(dateTimePattern),
            DateValidation.range(
                minDate = MIN_DATE_OF_BIRTH,
                maxDate = MAX_DATE_OF_BRITH
            )
        ).validate(form.dateOfBirth)
        return form.copy(
            usernameError = usernameError?.uiText(dictionary.errors),
            dateOfBirthError = dateOfBirthError?.uiText(dictionary.errors)
        )
    }
}