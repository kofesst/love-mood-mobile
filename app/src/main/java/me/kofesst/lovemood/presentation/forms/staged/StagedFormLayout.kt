@file:Suppress("unused")

package me.kofesst.lovemood.presentation.forms.staged

import androidx.activity.compose.BackHandler
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import me.kofesst.lovemood.app.LocalAppState
import me.kofesst.lovemood.presentation.forms.BaseFormViewModel
import me.kofesst.lovemood.presentation.forms.FormAction
import me.kofesst.lovemood.presentation.forms.FormState

@Stable
class StagedFormState<Model : Any, Form : FormState<Model>, Action : FormAction<Model, Form>, ViewModel : BaseFormViewModel<Model, Form, Action>>(
    private val stages: List<FormStage<Model, Form, Action, ViewModel>>,
    internal val coroutineScope: CoroutineScope,
    internal val pagerState: PagerState
) {
    val stageCount = stages.size

    val targetStageIndex by derivedStateOf { pagerState.targetPage }
    val targetStage by derivedStateOf { stages[targetStageIndex] }

    val currentStageIndex by derivedStateOf { pagerState.currentPage }
    val currentStage by derivedStateOf { stages[currentStageIndex] }

    val isFirstState by derivedStateOf { targetStageIndex == 0 }
    val isLastStage by derivedStateOf { targetStageIndex == stageCount - 1 }

    fun scrollToPreviousStage() {
        coroutineScope.launch {
            pagerState.animateScrollToPage(currentStageIndex - 1)
        }
    }

    fun scrollToNextStage() {
        coroutineScope.launch {
            pagerState.animateScrollToPage(currentStageIndex + 1)
        }
    }
}

// TODO: Fix so many generics

@Composable
fun <Model : Any, Form : FormState<Model>, Action : FormAction<Model, Form>, ViewModel : BaseFormViewModel<Model, Form, Action>> rememberStagedFormState(
    stages: List<FormStage<Model, Form, Action, ViewModel>>,
    coroutineScope: CoroutineScope
): StagedFormState<Model, Form, Action, ViewModel> {
    val pagerState = rememberPagerState { stages.size }
    return remember {
        StagedFormState(
            stages = stages,
            coroutineScope = coroutineScope,
            pagerState = pagerState
        )
    }
}

@Composable
fun <Model : Any, Form : FormState<Model>, Action : FormAction<Model, Form>, ViewModel : BaseFormViewModel<Model, Form, Action>> StagedFormLayout(
    modifier: Modifier = Modifier,
    progressModifier: Modifier = Modifier,
    controlsModifier: Modifier = Modifier,
    stages: List<FormStage<Model, Form, Action, ViewModel>>,
    viewModel: ViewModel,
    onSubmit: () -> Unit
) {
    val state = rememberStagedFormState(
        stages = stages,
        coroutineScope = rememberCoroutineScope()
    )
    Column(
        modifier = modifier
    ) {
        StagedFormProgress(
            modifier = progressModifier.fillMaxWidth(),
            stageCount = state.stageCount,
            currentStageIndex = state.currentStageIndex
        )
        HorizontalPager(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1.0f)
                .verticalScroll(rememberScrollState()),
            state = state.pagerState,
            userScrollEnabled = false,
            contentPadding = PaddingValues(horizontal = 20.dp),
            pageSpacing = 20.dp
        ) { stageIndex ->
            stages[stageIndex].Content(
                modifier = Modifier.fillMaxSize(),
                viewModel = viewModel
            )
        }
        val appState = LocalAppState.current
        val form by viewModel.formState.collectAsState()
        val canContinue by remember(form) {
            derivedStateOf { state.targetStage.continuePredicate(form) }
        }
        StagedFormControls(
            modifier = controlsModifier.fillMaxWidth(),
            isLastStage = state.isLastStage,
            canContinue = canContinue,
            onBackPressed = {
                if (state.isFirstState) appState.navigateUp()
                else state.scrollToPreviousStage()
            },
            onContinuePressed = {
                @Suppress("UNCHECKED_CAST")
                if (state.isLastStage) onSubmit()
                else state.scrollToNextStage()
            }
        )
    }
}

@Composable
private fun StagedFormProgress(
    modifier: Modifier = Modifier,
    stageCount: Int,
    currentStageIndex: Int,
    baseColor: Color = MaterialTheme.colorScheme.primary.copy(alpha = 0.30f),
    currentColor: Color = MaterialTheme.colorScheme.primary,
    indicatorThickness: Dp = 8.dp
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (stageIndex in 0 until stageCount) {
            Box(
                modifier = Modifier
                    .weight(1.0f)
                    .height(indicatorThickness)
                    .background(
                        color = if (stageIndex == currentStageIndex) currentColor
                        else baseColor,
                        shape = CircleShape
                    )
            )
        }
    }
}

@Composable
private fun StagedFormControls(
    modifier: Modifier = Modifier,
    isLastStage: Boolean,
    canContinue: Boolean,
    onBackPressed: () -> Unit,
    onContinuePressed: () -> Unit
) {
    BackHandler { onBackPressed() }
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(space = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = onBackPressed,
            contentPadding = PaddingValues(
                horizontal = 18.dp,
                vertical = 9.dp
            ),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                contentColor = MaterialTheme.colorScheme.primary,
                disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.50f),
                disabledContentColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.50f)
            ),
            shape = MaterialTheme.shapes.small
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                contentDescription = null
            )
        }
        Button(
            onClick = onContinuePressed,
            enabled = canContinue,
            contentPadding = PaddingValues(
                horizontal = 18.dp,
                vertical = 9.dp
            ),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.50f),
                disabledContentColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.50f)
            ),
            shape = MaterialTheme.shapes.small
        ) {
            CompositionLocalProvider(
                LocalTextStyle provides MaterialTheme.typography.bodyLarge.copy(
                    textAlign = TextAlign.Center
                ),
            ) {
                Crossfade(
                    modifier = Modifier.weight(1.0f),
                    targetState = isLastStage
                ) { isLastStage ->
                    if (isLastStage) Text(text = "Завершить")
                    else Text(text = "Продолжить")
                }
            }
        }
    }
}