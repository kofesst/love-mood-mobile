package me.kofesst.lovemood.async

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

private val defaultLoadingContent: @Composable () -> Unit = {
    CircularProgressIndicator()
}

private val defaultLazyLoadingContent: LazyListScope.() -> Unit = {
    item(key = "async_value_loading") {
        defaultLoadingContent()
    }
}

private val defaultFailedContent: @Composable (Exception?) -> Unit = { cause ->
    Text(text = cause?.localizedMessage ?: "Error")
}

private val defaultLazyFailedContent: LazyListScope.(Exception?) -> Unit = { cause ->
    item(key = "async_value_error") {
        defaultFailedContent(cause)
    }
}

@Composable
fun <T : Any> RequiredAsyncValueContent(
    asyncValue: AsyncValue<T>,
    onLoading: @Composable () -> Unit = defaultLoadingContent,
    onFailed: @Composable (Exception?) -> Unit = defaultFailedContent,
    onLoaded: @Composable (T) -> Unit
) {
    AsyncValueContent(
        asyncValue = asyncValue,
        onLoading = onLoading,
        onFailed = onFailed,
        onLoaded = { onLoaded(it!!) }
    )
}

@Composable
fun <T : Any> AsyncValueContent(
    asyncValue: AsyncValue<T>,
    onLoading: @Composable () -> Unit = defaultLoadingContent,
    onFailed: @Composable (Exception?) -> Unit = defaultFailedContent,
    onLoaded: @Composable (T?) -> Unit
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

fun <T : Any> LazyListScope.requiredAsyncValueContent(
    asyncValue: AsyncValue<T>,
    onLoading: LazyListScope.() -> Unit = defaultLazyLoadingContent,
    onFailed: LazyListScope.(Exception?) -> Unit = defaultLazyFailedContent,
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
    onLoading: LazyListScope.() -> Unit = defaultLazyLoadingContent,
    onFailed: LazyListScope.(Exception?) -> Unit = defaultLazyFailedContent,
    onLoaded: LazyListScope.(T?) -> Unit
) {
    if (asyncValue.status != me.kofesst.lovemood.async.AsyncValue.LoadStatus.Loaded && asyncValue.hasLoadedBefore) {
        val loadedValue = asyncValue.value
        onLoaded(loadedValue)
    } else {
        when (asyncValue.status) {
            me.kofesst.lovemood.async.AsyncValue.LoadStatus.Idle,
            me.kofesst.lovemood.async.AsyncValue.LoadStatus.Loading -> onLoading()

            me.kofesst.lovemood.async.AsyncValue.LoadStatus.Failed -> {
                val exception = asyncValue.error
                onFailed(exception)
            }

            me.kofesst.lovemood.async.AsyncValue.LoadStatus.Loaded -> {
                val loadedValue = asyncValue.value
                onLoaded(loadedValue)
            }
        }
    }
}