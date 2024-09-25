package me.kofesst.lovemood.presentation.forms.memory

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import me.kofesst.lovemood.core.models.PhotoMemory
import me.kofesst.lovemood.presentation.forms.staged.FormStage

interface MemoryFormStage :
    FormStage<PhotoMemory, MemoryFormState, MemoryFormAction, MemoryFormViewModel> {
    data object Main : MemoryFormStage {
        override val continuePredicate: (MemoryFormState) -> Boolean = MemoryFormState::isFilled

        @Composable
        override fun Content(
            modifier: Modifier,
            viewModel: MemoryFormViewModel
        ) {
            val form by viewModel.formState.collectAsState()
            MemoryFormContents.Content(
                modifier = modifier,
                form = form,
                onFormAction = viewModel::handleFormAction
            )
        }
    }
}