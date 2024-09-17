package me.kofesst.lovemood.presentation.forms

import android.util.Log
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import me.kofesst.lovemood.app.LocalAppState
import me.kofesst.lovemood.app.LocalDictionary
import me.kofesst.lovemood.core.ui.utils.alsoStatusBar
import me.kofesst.lovemood.ui.uiText

/**
 * Верхняя панель формы с кнопкой отправки.
 *
 * [onSubmit] - callback нажатия на кнопку отправки.
 *
 * [forms] - массив нескольких состояний форм.
 */
@Deprecated("Use content from StagedFormLayout.kt")
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
@Deprecated("Use content from StagedFormLayout.kt")
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
    val errorsDictionary = LocalDictionary.current.errors
    LaunchedEffect(Unit) {
        resultsFlow
            .flowWithLifecycle(lifecycleOwner.lifecycle)
            .collectLatest { formResult ->
                when (formResult) {
                    is FormResult.Failed -> {
                        Log.d("LoveMood", "Form failed: ${formResult.cause}")
                        appState.showSnackbar(
                            message = formResult.cause.uiText(errorsDictionary).string()
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