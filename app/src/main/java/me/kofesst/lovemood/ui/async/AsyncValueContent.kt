package me.kofesst.lovemood.ui.async

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text

private val defaultLoadingContent: LazyListScope.() -> Unit = {
    item(key = "async_value_loading") {
        CircularProgressIndicator()
    }
}

private val defaultFailedContent: LazyListScope.(Throwable?) -> Unit = { cause ->
    item(key = "async_value_error") {
        Text(text = cause?.localizedMessage ?: "Error")
    }
}

fun <T : Any> LazyListScope.requiredAsyncValueContent(
    asyncValue: AsyncValue<T>,
    onLoading: LazyListScope.() -> Unit = defaultLoadingContent,
    onFailed: LazyListScope.(Throwable?) -> Unit = defaultFailedContent,
    onLoaded: LazyListScope.(T) -> Unit
) {
    asyncValueContent(
        asyncValue = asyncValue,
        onLoading = onLoading,
        onFailed = onFailed,
        onLoaded = { onLoaded(it!!) }
    )
}

fun <T : Any> LazyListScope.asyncValueContent(
    asyncValue: AsyncValue<T>,
    onLoading: LazyListScope.() -> Unit = defaultLoadingContent,
    onFailed: LazyListScope.(Throwable?) -> Unit = defaultFailedContent,
    onLoaded: LazyListScope.(T?) -> Unit
) {
    if (asyncValue.status != AsyncValue.LoadStatus.Loaded && asyncValue.hasLoadedBefore) {
        val loadedValue = asyncValue.value
        onLoaded(loadedValue)
    } else {
        when (asyncValue.status) {
            AsyncValue.LoadStatus.Idle,
            AsyncValue.LoadStatus.Loading -> onLoading()

            AsyncValue.LoadStatus.Failed -> {
                val exception = asyncValue.error
                onFailed(exception)
            }

            AsyncValue.LoadStatus.Loaded -> {
                val loadedValue = asyncValue.value
                onLoaded(loadedValue)
            }
        }
    }
}