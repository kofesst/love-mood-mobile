package me.kofesst.lovemood.presentation.forms.profile

import dagger.hilt.android.lifecycle.HiltViewModel
import me.kofesst.lovemood.core.models.Profile
import me.kofesst.lovemood.core.usecases.AppUseCases
import me.kofesst.lovemood.features.date.DateTimePattern
import me.kofesst.lovemood.features.validation.operations.DateValidation
import me.kofesst.lovemood.features.validation.operations.StringValidation
import me.kofesst.lovemood.features.validation.operations.consumeOperations
import me.kofesst.lovemood.presentation.forms.BaseFormViewModel
import me.kofesst.lovemood.presentation.forms.FormMethod
import me.kofesst.lovemood.ui.text.dictionary.AppDictionary
import me.kofesst.lovemood.ui.text.dictionary.textHolder
import javax.inject.Inject

/**
 * ViewModel формы профиля пользователя.
 */
@HiltViewModel
open class ProfileFormViewModel @Inject constructor(
    private val useCases: AppUseCases,
    private val dateTimePattern: DateTimePattern,
    private val dictionary: AppDictionary
) : BaseFormViewModel<Profile, ProfileFormState, ProfileFormAction>(
    initialFormState = ProfileFormState(dateTimePattern),
    submitAction = ProfileFormAction.SubmitClicked
) {
    override suspend fun loadEditingModel(modelId: Int): Profile? {
        return useCases.profile.readById(modelId)
    }

    override suspend fun workWithModel(model: Profile, method: FormMethod): Profile {
        return when (method) {
            FormMethod.CreatingNewModel -> {
                val newProfileId = useCases.profile.create(model)
                model.copy(id = newProfileId)
            }

            FormMethod.EditingOldModel -> {
                useCases.profile.update(model)
                model
            }
        }
    }

    override fun validateForm(form: ProfileFormState): ProfileFormState {
        val usernameError = consumeOperations(
            StringValidation.required,
            StringValidation.length(ProfileFormState.USERNAME_LENGTH_RANGE)
        ).validate(form.username)
        val dateOfBirthError = consumeOperations(
            DateValidation.cast(dateTimePattern),
            DateValidation.range(
                minDate = ProfileFormState.MIN_DATE_OF_BIRTH,
                maxDate = ProfileFormState.MAX_DATE_OF_BRITH
            )
        ).validate(form.dateOfBirth)
        return form.copy(
            usernameError = usernameError?.textHolder(dictionary.errors),
            dateOfBirthError = dateOfBirthError?.textHolder(dictionary.errors)
        )
    }
}