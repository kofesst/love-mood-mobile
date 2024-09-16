package me.kofesst.lovemood.presentation.screens.relationship

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import me.kofesst.android.lovemood.navigation.AppScreen
import me.kofesst.lovemood.app.AppDestinations
import me.kofesst.lovemood.app.LocalAppState
import me.kofesst.lovemood.app.LocalDictionary
import me.kofesst.lovemood.core.ui.utils.alsoNavBar
import me.kofesst.lovemood.presentation.forms.FormMethod
import me.kofesst.lovemood.presentation.forms.FormResultsListener
import me.kofesst.lovemood.presentation.forms.formSubmitHeader
import me.kofesst.lovemood.presentation.forms.profile.profileFormContent
import me.kofesst.lovemood.presentation.forms.relationship.RelationshipFormAction
import me.kofesst.lovemood.presentation.forms.relationship.RelationshipFormViewModel
import me.kofesst.lovemood.presentation.forms.relationship.relationshipFormContent

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
        val appState = LocalAppState.current
        FormResultsListener(
            resultsFlow = viewModel.resultsFlow,
            onSuccessResult = {
                appState.navigate(AppDestinations.Home)
            }
        )

        val form by viewModel.formState.collectAsState()
        val screenDictionary = LocalDictionary.current.screens.relationshipForm
        LazyColumn(
            modifier = modifier,
            contentPadding = PaddingValues(20.dp).alsoNavBar(),
            verticalArrangement = Arrangement.spacedBy(30.dp),
        ) {
            formSubmitHeader(
                forms = arrayOf(form),
                onSubmit = {
                    viewModel.handleFormAction(
                        RelationshipFormAction.SubmitClicked
                    )
                }
            )
            item(key = "screen_header") {
                ScreenHeader(
                    modifier = Modifier.fillMaxWidth(),
                    formMethod = viewModel.formMethod.value
                )
            }
            profileFormContent(
                dictionary = screenDictionary.profileFormDictionary,
                form = form.partnerProfile,
                onFormAction = viewModel::handleProfileFormAction
            )
            relationshipFormContent(
                dictionary = screenDictionary.relationshipFormDictionary,
                form = form,
                onFormAction = viewModel::handleFormAction
            )
        }
    }

    @Composable
    private fun ScreenHeader(
        modifier: Modifier = Modifier,
        formMethod: FormMethod
    ) {
        val screenDictionary = LocalDictionary.current.screens.relationshipForm
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            when (formMethod) {
                FormMethod.CreatingNewModel -> {
                    Text(
                        text = screenDictionary.createHeaderTitle.string(),
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = screenDictionary.createHeaderSubtitle.string(),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Normal
                    )
                }

                FormMethod.EditingOldModel -> {
                    Text(
                        text = screenDictionary.editHeader.string(),
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}