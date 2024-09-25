package me.kofesst.lovemood.presentation.forms.profile

import me.kofesst.lovemood.core.models.Profile
import me.kofesst.lovemood.features.validation.operations.DateValidation
import me.kofesst.lovemood.features.validation.operations.StringValidation
import me.kofesst.lovemood.features.validation.operations.consumeOperations
import me.kofesst.lovemood.localization.AppLocalization
import me.kofesst.lovemood.presentation.forms.FormValidator
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Валидатор формы профиля.
 */
@Singleton
class ProfileFormValidator @Inject constructor(
    private val localization: AppLocalization
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
            DateValidation.range(
                minDate = MIN_DATE_OF_BIRTH,
                maxDate = MAX_DATE_OF_BRITH
            )
        ).validate(form.dateOfBirth)
        return form.copy(
            usernameError = usernameError?.let {
                localization.errors.fromValidationError(it)
            },
            dateOfBirthError = dateOfBirthError?.let {
                localization.errors.fromValidationError(it)
            }
        )
    }
}