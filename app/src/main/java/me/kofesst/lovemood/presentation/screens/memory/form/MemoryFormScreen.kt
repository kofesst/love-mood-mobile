package me.kofesst.lovemood.presentation.screens.memory.form

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
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
import me.kofesst.lovemood.app.dictionary
import me.kofesst.lovemood.core.ui.utils.alsoNavBar
import me.kofesst.lovemood.presentation.forms.FormMethod
import me.kofesst.lovemood.presentation.forms.FormResultsListener
import me.kofesst.lovemood.presentation.forms.buildFormLayout
import me.kofesst.lovemood.presentation.forms.formSubmitHeader
import me.kofesst.lovemood.presentation.forms.memory.MemoryFormAction
import me.kofesst.lovemood.presentation.forms.memory.MemoryFormViewModel
import me.kofesst.lovemood.presentation.forms.memory.memoryFormContent

object MemoryFormScreen : AppScreen() {
    @Composable
    override fun ScreenContent(
        modifier: Modifier,
        navBackStackEntry: NavBackStackEntry
    ) {
        val editingMemoryId = rememberArgument(
            argument = AppDestinations.Forms.Memory.editingIdArgument,
            navBackStackEntry = navBackStackEntry
        ).takeIf { it > 0 }
        val viewModel = hiltViewModel<MemoryFormViewModel>()
        LaunchedEffect(editingMemoryId) {
            viewModel.prepareForm(editingMemoryId)
        }

        Content(modifier, viewModel)
    }

    @Composable
    private fun Content(
        modifier: Modifier = Modifier,
        viewModel: MemoryFormViewModel,
    ) {
        val appState = LocalAppState.current
        FormResultsListener(
            resultsFlow = viewModel.resultsFlow,
            onSuccessResult = { appState.navigateUp() }
        )

        val screenDictionary = dictionary.screens.memoryForm
        val form by viewModel.formState.collectAsState()
        LazyColumn(
            modifier = modifier,
            contentPadding = PaddingValues(20.dp).alsoNavBar(),
            verticalArrangement = Arrangement.spacedBy(30.dp),
        ) {
            formSubmitHeader(
                forms = arrayOf(form),
                onSubmit = {
                    viewModel.handleFormAction(
                        MemoryFormAction.SubmitClicked
                    )
                }
            )
            item(key = "screen_header") {
                ScreenHeader(formMethod = viewModel.formMethod)
            }
            buildFormLayout(
                viewModels = arrayOf(viewModel),
                loadingContent = {
                    item {
                        CircularProgressIndicator()
                    }
                },
                content = {
                    memoryFormContent(
                        dictionary = screenDictionary.formDictionary,
                        form = form,
                        onFormAction = viewModel::handleFormAction
                    )
                }
            )
        }
    }

    @Composable
    private fun ScreenHeader(
        modifier: Modifier = Modifier,
        formMethod: FormMethod
    ) {
        val screenDictionary = LocalDictionary.current.screens.memoryForm
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