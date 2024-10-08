package me.kofesst.lovemood.presentation.forms.profile

import dagger.hilt.android.lifecycle.HiltViewModel
import me.kofesst.lovemood.core.models.Profile
import me.kofesst.lovemood.core.usecases.AppUseCases
import me.kofesst.lovemood.features.date.DateTimePattern
import me.kofesst.lovemood.presentation.forms.FormMethod
import me.kofesst.lovemood.ui.text.dictionary.AppDictionary
import javax.inject.Inject

@HiltViewModel
class UserProfileFormViewModel @Inject constructor(
    private val useCases: AppUseCases,
    dateTimePattern: DateTimePattern,
    dictionary: AppDictionary
) : ProfileFormViewModel(useCases, dateTimePattern, dictionary) {
    override suspend fun workWithModel(model: Profile, method: FormMethod): Profile {
        return when (method) {
            FormMethod.CreatingNewModel -> {
                val newProfileId = useCases.profile.create(model)
                val settings = useCases.dataStore.getSettings()
                useCases.dataStore.saveSettings(
                    settings.copy(
                        userProfileId = newProfileId
                    )
                )
                model.copy(id = newProfileId)
            }

            FormMethod.EditingOldModel -> {
                useCases.profile.update(model)
                model
            }
        }
    }
}