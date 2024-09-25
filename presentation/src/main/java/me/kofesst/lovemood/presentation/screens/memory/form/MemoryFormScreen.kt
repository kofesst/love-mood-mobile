package me.kofesst.lovemood.presentation.screens.memory.form

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import me.kofesst.android.lovemood.navigation.AppScreen
import me.kofesst.lovemood.presentation.Destinations
import me.kofesst.lovemood.presentation.LocalAppState
import me.kofesst.lovemood.presentation.forms.FormResultsListener
import me.kofesst.lovemood.presentation.forms.memory.MemoryFormAction
import me.kofesst.lovemood.presentation.forms.memory.MemoryFormStage
import me.kofesst.lovemood.presentation.forms.memory.MemoryFormViewModel
import me.kofesst.lovemood.presentation.forms.staged.StagedFormLayout

object MemoryFormScreen : AppScreen() {
    @Composable
    override fun ScreenContent(
        modifier: Modifier,
        navBackStackEntry: NavBackStackEntry
    ) {
        val editingMemoryId = rememberArgument(
            argument = Destinations.MemoryForm.editingIdArgument,
            navBackStackEntry = navBackStackEntry
        ).takeIf { it > 0 }
        val viewModel = hiltViewModel<MemoryFormViewModel>()
        LaunchedEffect(editingMemoryId) {
            viewModel.setEditingModel(editingMemoryId)
        }
        val appState = LocalAppState.current
        FormResultsListener(
            resultsFlow = viewModel.resultsFlow,
            onSuccessResult = {
                appState.navigate(Destinations.MemoriesOverview)
            }
        )
        Content(modifier, viewModel)
    }

    @Composable
    private fun Content(
        modifier: Modifier = Modifier,
        viewModel: MemoryFormViewModel,
    ) {
        Box(modifier) {
            StagedFormLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
                    .navigationBarsPadding(),
                progressModifier = Modifier.padding(20.dp),
                controlsModifier = Modifier.padding(20.dp),
                stages = listOf(MemoryFormStage.Main),
                viewModel = viewModel,
                onSubmit = {
                    viewModel.handleFormAction(
                        MemoryFormAction.SubmitClicked
                    )
                }
            )
        }
    }
}