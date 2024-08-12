package me.kofesst.lovemood.presentation.screens.profile

import dagger.hilt.android.lifecycle.HiltViewModel
import me.kofesst.lovemood.core.models.Profile
import me.kofesst.lovemood.core.usecases.AppUseCases
import me.kofesst.lovemood.features.date.DateTimePattern
import me.kofesst.lovemood.features.validation.operations.DateValidation
import me.kofesst.lovemood.features.validation.operations.StringValidation
import me.kofesst.lovemood.features.validation.operations.consumeOperations
import me.kofesst.lovemood.presentation.forms.BaseFormViewModel
import me.kofesst.lovemood.presentation.forms.profile.ProfileFormAction
import me.kofesst.lovemood.presentation.forms.profile.ProfileFormState
import me.kofesst.lovemood.ui.text.dictionary.AppDictionary
import me.kofesst.lovemood.ui.text.dictionary.textHolder
import javax.inject.Inject

@HiltViewModel
class UserProfileFormViewModel @Inject constructor(
    private val useCases: AppUseCases,
    private val dateTimePattern: DateTimePattern,
    private val dictionary: AppDictionary
) : BaseFormViewModel<Profile, ProfileFormState, ProfileFormAction>(
    initialFormState = ProfileFormState(dateTimePattern),
    submitAction = ProfileFormAction.SubmitClicked
) {
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

    override suspend fun sendModel(model: Profile) {
        val newProfileId = useCases.profile.create(model)
        val settings = useCases.dataStore.getSettings()
        useCases.dataStore.saveSettings(
            newSettings = settings.copy(
                userProfileId = newProfileId
            )
        )
    }
}