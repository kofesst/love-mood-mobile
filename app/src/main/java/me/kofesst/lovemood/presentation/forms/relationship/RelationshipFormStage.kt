package me.kofesst.lovemood.presentation.forms.relationship

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import me.kofesst.lovemood.core.models.Relationship
import me.kofesst.lovemood.presentation.forms.staged.FormStage

sealed interface RelationshipFormStage :
    FormStage<Relationship, RelationshipFormState, RelationshipFormAction, RelationshipFormViewModel> {
    /**
     * Этап выбора фотографии профиля партнера.
     */
    data object SelectingPartnerPicture : RelationshipFormStage {
        override val continuePredicate: (RelationshipFormState) -> Boolean = { true }

        @Composable
        override fun Content(
            modifier: Modifier,
            viewModel: RelationshipFormViewModel
        ) {
            val form by viewModel.formState.collectAsState()
            RelationshipFormContents.SelectingPartnerPictureStageContent(
                modifier = modifier,
                form = form,
                onProfileFormAction = viewModel::handleProfileFormAction
            )
        }
    }

    /**
     * Этап заполнения информации об отношениях.
     */
    data object EnteringData : RelationshipFormStage {
        override val continuePredicate: (RelationshipFormState) -> Boolean = { form ->
            form.partnerProfile.username.isNotBlank() &&
                    form.partnerProfile.dateOfBirth.isNotBlank() &&
                    form.startDate.isNotBlank()
        }

        @Composable
        override fun Content(
            modifier: Modifier,
            viewModel: RelationshipFormViewModel
        ) {
            val form by viewModel.formState.collectAsState()
            RelationshipFormContents.EnteringDataStageContent(
                modifier = modifier,
                form = form,
                onFormAction = viewModel::handleFormAction,
                onProfileFormAction = viewModel::handleProfileFormAction
            )
        }
    }
}