package me.kofesst.lovemood.presentation.forms

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.flowWithLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import me.kofesst.lovemood.presentation.app.LocalAppState
import me.kofesst.lovemood.ui.alsoStatusBar

/**
 * Верхняя панель формы с кнопкой отправки.
 *
 * [onSubmit] - callback нажатия на кнопку отправки.
 *
 * [forms] - массив нескольких состояний форм.
 */
@OptIn(ExperimentalFoundationApi::class)
fun LazyListScope.formSubmitHeader(
    onSubmit: () -> Unit,
    vararg forms: FormState<*>
) {
    stickyHeader(key = "form_submit_header") {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(PaddingValues(20.dp).alsoStatusBar()),
            horizontalArrangement = Arrangement.End
        ) {
            AnimatedVisibility(
                visible = forms.all { it.isFilled },
                enter = fadeIn() + slideInVertically { -it },
                exit = fadeOut() + slideOutVertically { -it }
            ) {
                IconButton(
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                        contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    onClick = onSubmit
                ) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        imageVector = Icons.AutoMirrored.Rounded.ArrowForwardIos,
                        contentDescription = null
                    )
                }
            }
        }
    }
}

/**
 * Базовый макет формы.
 *
 * [viewModels] - вью модели нескольких форм.
 *
 * [loadingContent] - контент, отображаемый при подготовке формы.
 *
 * [content] - основной контент формы.
 */
fun LazyListScope.buildFormLayout(
    vararg viewModels: BaseFormViewModel<*, *, *>,
    loadingContent: LazyListScope.() -> Unit,
    content: LazyListScope.() -> Unit
) {
    if (viewModels.all { it.preparedState.value }) {
        content()
    } else {
        loadingContent()
    }
}

/**
 * Слушатель результатов формы [resultsFlow].
 *
 * [onFailedResult] - callback, вызываемый при неудачном
 * результате отправки формы.
 *
 * [onSuccessResult] - callback, вызываемый при успешном
 * результате отправки формы.
 *
 * [onResult] - callback, вызываемый при любом результате
 * отправки формы.
 */
@Composable
fun <Model : Any> FormResultsListener(
    resultsFlow: Flow<FormResult<Model>>,
    onFailedResult: (Throwable) -> Unit = {},
    onSuccessResult: (Model) -> Unit = {},
    onResult: (FormResult<Model>) -> Unit = {}
) {
    val appState = LocalAppState.current
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(Unit) {
        resultsFlow
            .flowWithLifecycle(lifecycleOwner.lifecycle)
            .collectLatest { formResult ->
                when (formResult) {
                    is FormResult.Failed -> {
                        appState.showSnackbar(
                            message = formResult.cause.toString()
                        )
                        onFailedResult(formResult.cause)
                    }

                    is FormResult.Success -> {
                        onSuccessResult(formResult.model)
                    }
                }
                onResult(formResult)
            }
    }
}