package me.kofesst.lovemood.presentation.forms.relationship

import android.content.Context
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import me.kofesst.lovemood.core.models.Relationship
import me.kofesst.lovemood.core.usecases.AppUseCases
import me.kofesst.lovemood.database.ProfileNotFoundException
import me.kofesst.lovemood.features.date.DateTimePattern
import me.kofesst.lovemood.features.validation.operations.DateValidation
import me.kofesst.lovemood.features.validation.operations.consumeOperations
import me.kofesst.lovemood.localization.dictionary.AppDictionary
import me.kofesst.lovemood.presentation.forms.BaseFormViewModel
import me.kofesst.lovemood.presentation.forms.FormMethod
import me.kofesst.lovemood.ui.uiText
import javax.inject.Inject

/**
 * ViewModel формы отношений.
 */
@HiltViewModel
class RelationshipFormViewModel @Inject constructor(
    private val useCases: AppUseCases,
    private val dateTimePattern: DateTimePattern,
    private val dictionary: AppDictionary
) : BaseFormViewModel<Relationship, RelationshipFormState, RelationshipFormAction>(
    initialFormState = RelationshipFormState(dateTimePattern),
    submitAction = RelationshipFormAction.SubmitClicked
) {
    override suspend fun loadEditingModel(modelId: Int): Relationship? {
        return useCases.relationship.readById(modelId)
    }

    override suspend fun workWithModel(model: Relationship, method: FormMethod): Relationship {
        return when (method) {
            FormMethod.CreatingNewModel -> {
                val userProfileId = useCases.dataStore.getSettings().userProfileId
                    ?: throw ProfileNotFoundException()
                val userProfile = useCases.profile.readById(userProfileId)
                    ?: throw ProfileNotFoundException()
                val relationship = model.copy(
                    userProfile = userProfile
                )
                val newRelationshipId = useCases.relationship.create(relationship)
                model.copy(id = newRelationshipId)
            }

            FormMethod.EditingOldModel -> {
                val userProfileId = useCases.dataStore.getSettings().userProfileId
                    ?: throw ProfileNotFoundException()
                val userProfile = useCases.profile.readById(userProfileId)
                    ?: throw ProfileNotFoundException()
                val relationship = model.copy(
                    userProfile = userProfile
                )
                useCases.relationship.update(relationship)
                relationship
            }
        }
    }

    override fun validateForm(form: RelationshipFormState): RelationshipFormState {
        val startDateError = consumeOperations(
            DateValidation.cast(dateTimePattern),
            DateValidation.range(
                minDate = RelationshipFormState.MIN_START_DATE,
                maxDate = RelationshipFormState.MAX_START_DATE
            )
        ).validate(form.startDate)
        return form.copy(
            startDateError = startDateError?.uiText(dictionary.errors)
        )
    }
}