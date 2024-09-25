package me.kofesst.lovemood.presentation.forms.relationship

import me.kofesst.lovemood.core.models.Relationship
import me.kofesst.lovemood.presentation.forms.FormAction
import java.time.LocalDate

sealed interface RelationshipFormAction : FormAction<Relationship, RelationshipFormState> {
    data class StartDateChanged(private val value: LocalDate) : RelationshipFormAction {
        override fun applyToForm(currentForm: RelationshipFormState): RelationshipFormState {
            return currentForm.copy(startDate = value)
        }
    }

    data object SubmitClicked : RelationshipFormAction
}