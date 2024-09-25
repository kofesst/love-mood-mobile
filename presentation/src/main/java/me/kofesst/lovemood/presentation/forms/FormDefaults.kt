package me.kofesst.lovemood.presentation.forms

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import me.kofesst.lovemood.presentation.LocalAppState

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
                        Log.d("LoveMood", "Form failed: ${formResult.cause}")
                        appState.showSnackbar(
                            message = formResult.cause.toString() // TODO localize simple errors
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