package me.kofesst.lovemood.presentation.forms.relationship

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import me.kofesst.lovemood.core.interactor.UseCaseParams
import me.kofesst.lovemood.core.interactor.relationship.RelationshipInteractor
import me.kofesst.lovemood.core.models.Relationship
import me.kofesst.lovemood.features.date.DateTimePattern
import me.kofesst.lovemood.presentation.forms.BaseFormViewModel
import me.kofesst.lovemood.presentation.forms.FormMethod
import me.kofesst.lovemood.presentation.forms.profile.ProfileFormAction
import javax.inject.Inject

/**
 * ViewModel формы отношений.
 */
@HiltViewModel
class RelationshipFormViewModel @Inject constructor(
    private val relationshipInteractor: RelationshipInteractor,
    dateTimePattern: DateTimePattern,
    validator: RelationshipFormValidator
) : BaseFormViewModel<Relationship, RelationshipFormState, RelationshipFormAction>(
    initialFormState = RelationshipFormState(dateTimePattern),
    validator = validator,
    submitActionClass = RelationshipFormAction.SubmitClicked.javaClass
) {
    /**
     * Устанавливает флаг типа формы: создание новой модели или редактирование старой.
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
                relationshipInteractor.get().getOrNull()?.let { userRelationship ->
                    manuallyEditForm { currentForm ->
                        currentForm.fromModel(userRelationship)
                    }
                }
            }
            prepareForm()
        }
    }

    /**
     * Обрабатывает событие в форме профиля.
     *
     * [action] - событие в форме профиля.
     */
    fun handleProfileFormAction(action: ProfileFormAction) {
        manuallyEditForm { currentForm ->
            currentForm.copy(
                partnerProfile = action.applyToForm(currentForm.partnerProfile)
            )
        }
    }

    override suspend fun workWithModel(model: Relationship, method: FormMethod): Relationship {
        when (method) {
            FormMethod.CreatingNewModel -> {
                return relationshipInteractor.create(
                    params = UseCaseParams.Single.with(model)
                ).getOrThrow()
            }

            FormMethod.EditingOldModel -> {
                relationshipInteractor.update(
                    params = UseCaseParams.Single.with(model)
                )
                return model
            }
        }
    }
}