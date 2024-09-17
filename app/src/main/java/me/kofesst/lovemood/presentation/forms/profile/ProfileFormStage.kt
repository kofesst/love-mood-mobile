package me.kofesst.lovemood.presentation.forms.profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import me.kofesst.lovemood.core.models.Profile
import me.kofesst.lovemood.presentation.forms.staged.FormStage

/**
 * Интерфейс этапа формы профиля.
 */
sealed interface ProfileFormStage :
    FormStage<Profile, ProfileFormState, ProfileFormAction, ProfileFormViewModel> {
    /**
     * Этап выбора фотографии профиля.
     */
    data object SelectingPicture : ProfileFormStage {
        override val continuePredicate: (ProfileFormState) -> Boolean = { true }

        @Composable
        override fun Content(
            modifier: Modifier,
            viewModel: ProfileFormViewModel
        ) {
            val form by viewModel.formState.collectAsState()
            ProfileFormContents.SelectingPictureStageContent(
                modifier = modifier,
                form = form,
                onFormAction = viewModel::handleFormAction
            )
        }
    }

    /**
     * Этап заполнения информации о профиле.
     */
    data object EnteringData : ProfileFormStage {
        override val continuePredicate: (ProfileFormState) -> Boolean = { form ->
            form.username.isNotBlank() && form.dateOfBirth.isNotBlank()
        }

        @Composable
        override fun Content(
            modifier: Modifier,
            viewModel: ProfileFormViewModel
        ) {
            val form by viewModel.formState.collectAsState()
            ProfileFormContents.EnteringDataStageContent(
                modifier = modifier,
                form = form,
                onFormAction = viewModel::handleFormAction
            )
        }
    }
}