package me.kofesst.lovemood.presentation.forms.relationship

import me.kofesst.lovemood.core.models.Profile
import me.kofesst.lovemood.core.models.Relationship
import me.kofesst.lovemood.presentation.forms.FormAction

sealed class RelationshipFormAction : FormAction<Relationship, RelationshipFormState>() {
    data class StartDateChanged(private val value: String) : RelationshipFormAction() {
        override fun applyToForm(currentForm: RelationshipFormState): RelationshipFormState {
            return currentForm.copy(startDate = value)
        }
    }

    data class PartnerProfileUpdated(private val value: Profile): RelationshipFormAction() {
        override fun applyToForm(currentForm: RelationshipFormState): RelationshipFormState {
            return currentForm.copy(partnerProfile = value)
        }
    }

    data object SubmitClicked : RelationshipFormAction()
}