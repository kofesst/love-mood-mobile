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
import me.kofesst.lovemood.presentation.forms.profile.ProfileFormAction
import me.kofesst.lovemood.presentation.forms.profile.ProfileFormViewModel
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
        val editingRelationshipId = rememberArgument(
            argument = AppDestinations.Forms.Relationship.editingIdArgument,
            navBackStackEntry = navBackStackEntry
        ).takeIf { it > 0 }
        val profileFormViewModel = hiltViewModel<ProfileFormViewModel>()
        val relationshipFormViewModel = hiltViewModel<RelationshipFormViewModel>()
        LaunchedEffect(editingRelationshipId) {
            relationshipFormViewModel.prepareForm(
                editingModelId = editingRelationshipId,
                callback = { editingRelationship ->
                    profileFormViewModel.prepareForm(
                        editingModel = editingRelationship?.partnerProfile
                    )
                }
            )
        }

        Content(modifier, profileFormViewModel, relationshipFormViewModel)
    }

    @Composable
    private fun Content(
        modifier: Modifier = Modifier,
        profileFormViewModel: ProfileFormViewModel,
        relationshipFormViewModel: RelationshipFormViewModel
    ) {
        val appState = LocalAppState.current
        FormResultsListener(
            resultsFlow = profileFormViewModel.resultsFlow,
            onSuccessResult = { partnerProfile ->
                relationshipFormViewModel.handleFormAction(
                    RelationshipFormAction.PartnerProfileUpdated(partnerProfile)
                )
            },
            onResult = {
                relationshipFormViewModel.handleFormAction(
                    RelationshipFormAction.SubmitClicked
                )
            }
        )
        FormResultsListener(
            resultsFlow = relationshipFormViewModel.resultsFlow,
            onSuccessResult = {
                appState.navigate(AppDestinations.Home)
            }
        )

        val profileForm by profileFormViewModel.formState.collectAsState()
        val relationshipForm by relationshipFormViewModel.formState.collectAsState()
        val screenDictionary = LocalDictionary.current.screens.relationshipForm
        LazyColumn(
            modifier = modifier,
            contentPadding = PaddingValues(20.dp).alsoNavBar(),
            verticalArrangement = Arrangement.spacedBy(30.dp),
        ) {
            formSubmitHeader(
                forms = arrayOf(profileForm, relationshipForm),
                onSubmit = {
                    profileFormViewModel.handleFormAction(
                        ProfileFormAction.SubmitClicked
                    )
                }
            )
            item(key = "screen_header") {
                ScreenHeader(
                    modifier = Modifier.fillMaxWidth(),
                    formMethod = relationshipFormViewModel.formMethod
                )
            }
            profileFormContent(
                dictionary = screenDictionary.profileFormDictionary,
                form = profileForm,
                onFormAction = profileFormViewModel::handleFormAction
            )
            relationshipFormContent(
                dictionary = screenDictionary.relationshipFormDictionary,
                form = relationshipForm,
                onFormAction = relationshipFormViewModel::handleFormAction
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