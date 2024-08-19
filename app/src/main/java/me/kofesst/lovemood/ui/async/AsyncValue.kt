package me.kofesst.lovemood.ui.async

import android.util.Log
import androidx.compose.runtime.MutableState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow

typealias AsyncValuesList<T> = AsyncValue<List<T>>

data class AsyncValue<T : Any>(
    val value: T? = null,
    val error: Exception? = null,
    val status: LoadStatus = LoadStatus.Idle,
    val hasLoadedBefore: Boolean = false
) {
    inline fun load(
        crossinline block: suspend () -> T?
    ): Flow<AsyncValue<T>> = flow {
        emit(copy(status = LoadStatus.Loading))
        try {
            val loadedValue = block()
            emit(
                copy(
                    value = loadedValue,
                    error = null,
                    status = LoadStatus.Loaded,
                    hasLoadedBefore = true
                )
            )
        } catch (unhandled: Exception) {
            Log.d("LoveMood", "Got an error on loading async value: $unhandled")
            emit(
                copy(
                    value = null,
                    error = unhandled,
                    status = LoadStatus.Failed,
                    hasLoadedBefore = true
                )
            )
        }
    }

    enum class LoadStatus {
        Idle, Loading, Loaded, Failed
    }
}

suspend inline fun <reified T : Any> MutableState<AsyncValue<T>>.load(
    crossinline block: suspend () -> T?
) {
    this.value
        .load(block)
        .collectLatest { value ->
            this.value = value
        }
}