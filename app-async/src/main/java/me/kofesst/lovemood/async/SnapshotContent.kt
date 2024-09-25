package me.kofesst.lovemood.async

import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

typealias SnapshotContentScope<T> = AnimatedContentTransitionScope<SnapshotValue<T>>
typealias SnapshotContentTransform<T> = SnapshotContentScope<T>.() -> ContentTransform

private const val DEFAULT_FADE_DURATION = 1000

private fun <T : Any> SnapshotContentScope<T>.defaultSnapshotContentTransform(): ContentTransform {
    val hasDifferenceInValues = initialState.value != targetState.value
    return if (hasDifferenceInValues) {
        Log.d("LoveMoodAsync", "Snapshot content diff:")
        Log.d("LoveMoodAsync", "    Initial = ${initialState.value}")
        Log.d("LoveMoodAsync", "    Target = ${targetState.value}")
        val enter = fadeIn(animationSpec = tween(DEFAULT_FADE_DURATION))
        val exit = fadeOut(animationSpec = tween(DEFAULT_FADE_DURATION))
        enter.togetherWith(exit)
    } else {
        Log.d("LoveMoodAsync", "No difference between states")
        val enter = EnterTransition.None
        val exit = ExitTransition.None
        enter.togetherWith(exit)
    }
}

@Composable
fun <T : Any> SnapshotContent(
    modifier: Modifier = Modifier,
    snapshot: SnapshotValue<T>,
    loadingContent: @Composable () -> Unit = {
        CircularProgressIndicator()
    },
    failedContent: @Composable (cause: Exception?) -> Unit,
    loadedContent: @Composable (value: T?) -> Unit,
    contentTransform: SnapshotContentTransform<T> = {
        defaultSnapshotContentTransform()
    }
) {
    AnimatedContent(
        modifier = modifier,
        targetState = snapshot,
        transitionSpec = contentTransform
    ) { state ->
        when {
            state.hasLoadedBefore -> loadedContent(state.value)
            else -> when (state.status) {
                SnapshotStatus.Failed -> failedContent(snapshot.error)
                SnapshotStatus.Loaded -> loadedContent(snapshot.value)
                else -> loadingContent()
            }
        }
    }
}

@Composable
fun <T : Any> RequiredSnapshotContent(
    modifier: Modifier = Modifier,
    snapshot: SnapshotValue<T>,
    loadingContent: @Composable () -> Unit = {
        CircularProgressIndicator()
    },
    failedContent: @Composable (cause: Exception?) -> Unit,
    loadedContent: @Composable (value: T) -> Unit,
    contentTransform: SnapshotContentTransform<T> = {
        defaultSnapshotContentTransform()
    }
) {
    SnapshotContent(
        modifier = modifier,
        snapshot = snapshot,
        loadingContent = loadingContent,
        failedContent = failedContent,
        loadedContent = { nullable ->
            loadedContent(nullable!!)
        },
        contentTransform = contentTransform
    )
}