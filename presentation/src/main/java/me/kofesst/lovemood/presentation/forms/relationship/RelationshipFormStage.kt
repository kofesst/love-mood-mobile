package me.kofesst.lovemood.presentation.forms.relationship

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import me.kofesst.lovemood.core.models.Relationship
import me.kofesst.lovemood.presentation.forms.profile.ProfileFormContents
import me.kofesst.lovemood.presentation.forms.staged.FormStage
import me.kofesst.lovemood.presentation.localization

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
            ProfileFormContents.SelectingPictureStageContent(
                modifier = modifier,
                localization = localization.screens.partnerProfileForm,
                form = form.partnerProfile,
                onFormAction = viewModel::handleProfileFormAction
            )
        }
    }

    /**
     * Этап заполнения информации об отношениях.
     */
    data object EnteringData : RelationshipFormStage {
        override val continuePredicate: (RelationshipFormState) -> Boolean = { form ->
            form.partnerProfile.isFilled
        }

        @Composable
        override fun Content(
            modifier: Modifier,
            viewModel: RelationshipFormViewModel
        ) {
            val form by viewModel.formState.collectAsState()
            ProfileFormContents.EnteringDataStageContent(
                modifier = modifier,
                localization = localization.screens.partnerProfileForm,
                form = form.partnerProfile,
                onFormAction = viewModel::handleProfileFormAction
            )
        }
    }

    /**
     * Этап заполнения информации об отношениях.
     */
    data object LoveStartDate : RelationshipFormStage {
        override val continuePredicate: (RelationshipFormState) -> Boolean = { true }

        @Composable
        override fun Content(
            modifier: Modifier,
            viewModel: RelationshipFormViewModel
        ) {
            val form by viewModel.formState.collectAsState()
            RelationshipFormContents.LoveStartDateContent(
                modifier = modifier,
                localization = localization.screens.relationshipForm,
                form = form,
                onFormAction = viewModel::handleFormAction
            )
        }
    }
}