package me.kofesst.lovemood.presentation.screens.relationship

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
import me.kofesst.lovemood.app.AppDestinations
import me.kofesst.lovemood.app.LocalAppState
import me.kofesst.lovemood.presentation.forms.FormResultsListener
import me.kofesst.lovemood.presentation.forms.relationship.RelationshipFormAction
import me.kofesst.lovemood.presentation.forms.relationship.RelationshipFormStage
import me.kofesst.lovemood.presentation.forms.relationship.RelationshipFormViewModel
import me.kofesst.lovemood.presentation.forms.staged.StagedFormLayout

object RelationshipFormScreen : AppScreen() {
    @Composable
    override fun ScreenContent(
        modifier: Modifier,
        navBackStackEntry: NavBackStackEntry
    ) {
        val isEditing = rememberArgument(
            argument = AppDestinations.Forms.Relationship.isEditingArgument,
            navBackStackEntry = navBackStackEntry
        )
        val viewModel = hiltViewModel<RelationshipFormViewModel>()
        LaunchedEffect(isEditing) {
            viewModel.setIsEditing(isEditing)
        }
        val appState = LocalAppState.current
        FormResultsListener(
            resultsFlow = viewModel.resultsFlow,
            onSuccessResult = {
                appState.navigate(AppDestinations.Home)
            }
        )
        Content(
            modifier = modifier,
            viewModel = viewModel
        )
    }

    @Composable
    private fun Content(
        modifier: Modifier = Modifier,
        viewModel: RelationshipFormViewModel
    ) {
        Box(modifier) {
            StagedFormLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
                    .navigationBarsPadding(),
                progressModifier = Modifier.padding(20.dp),
                controlsModifier = Modifier.padding(20.dp),
                stages = listOf(
                    RelationshipFormStage.SelectingPartnerPicture,
                    RelationshipFormStage.EnteringData
                ),
                viewModel = viewModel,
                onSubmit = {
                    viewModel.handleFormAction(
                        RelationshipFormAction.SubmitClicked
                    )
                }
            )
        }
    }
}