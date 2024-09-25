package me.kofesst.lovemood.presentation.forms.profile

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import me.kofesst.lovemood.core.interactor.UseCaseParams
import me.kofesst.lovemood.core.interactor.profile.UserProfileInteractor
import me.kofesst.lovemood.core.models.Profile
import me.kofesst.lovemood.presentation.forms.BaseFormViewModel
import me.kofesst.lovemood.presentation.forms.FormMethod
import javax.inject.Inject

/**
 * ViewModel формы профиля пользователя.
 */
@HiltViewModel
open class ProfileFormViewModel @Inject constructor(
    private val profileInteractor: UserProfileInteractor,
    validator: ProfileFormValidator
) : BaseFormViewModel<Profile, ProfileFormState, ProfileFormAction>(
    initialFormState = ProfileFormState(),
    validator = validator,
    submitActionClass = ProfileFormAction.SubmitClicked.javaClass
) {
    /**
     * Устанавливает флаг типа формы - создание новой модели или редактирование старой.
     *
     * [isEditing] - флаг типа формы.
     */
    fun setIsEditing(isEditing: Boolean) {
        viewModelScope.launch {
            changeFormMethod(
                if (isEditing) FormMethod.EditingOldModel
                else FormMethod.CreatingNewModel
            )
            if (isEditing) {
                profileInteractor.get().getOrNull()?.let { userProfile ->
                    manuallyEditForm { currentForm ->
                        currentForm.fromModel(userProfile)
                    }
                }
            }
            prepareForm()
        }
    }

    override suspend fun workWithModel(model: Profile, method: FormMethod): Profile {
        when (method) {
            FormMethod.CreatingNewModel -> {
                return profileInteractor.create(
                    params = UseCaseParams.Single.with(model)
                ).getOrThrow()
            }

            FormMethod.EditingOldModel -> {
                profileInteractor.update(
                    params = UseCaseParams.Single.with(model)
                )
                return model
            }
        }
    }
}