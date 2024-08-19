package me.kofesst.lovemood.core.ui.components.image

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import me.kofesst.lovemood.core.ui.utils.ByteArrayImage

class ImageViewerState {
    private val _imageState = mutableStateOf(byteArrayOf())
    val imageState: State<ByteArray> = _imageState

    private val _isVisible = mutableStateOf(false)
    val visibleState: State<Boolean> = _isVisible

    fun showItem(image: ByteArray) {
        _imageState.value = image
        _isVisible.value = true
    }

    fun close() {
        _isVisible.value = false
    }
}

@Composable
fun ImageViewer(
    modifier: Modifier = Modifier,
    state: ImageViewerState,
    content: @Composable () -> Unit
) {
    Box(modifier) {
        content()
        ImageViewer(
            modifier = Modifier.zIndex(1f),
            state = state
        )
    }
}

@Composable
fun ImageViewer(
    modifier: Modifier = Modifier,
    state: ImageViewerState
) {
    val image by state.imageState
    val isVisible by state.visibleState
    BackHandler(enabled = isVisible) {
        state.close()
    }
    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn() + scaleIn(),
        exit = fadeOut() + scaleOut()
    ) {
        ImageViewerContent(
            modifier = modifier,
            imageContent = image
        )
    }
}

@Composable
private fun ImageViewerContent(
    modifier: Modifier = Modifier,
    imageContent: ByteArray,
) {
    var scale by remember { mutableStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    var size by remember { mutableStateOf(IntSize.Zero) }
    Box(
        modifier = modifier
            .onSizeChanged { size = it }
            .pointerInput(Unit) {
                detectTransformGestures { _, gesturePan, gestureZoom, _ ->
                    scale = (scale * gestureZoom).coerceIn(1f, 3f)
                    val newOffset = offset + gesturePan
                    val maxOffset = Offset(
                        x = size.width * (scale - 1) / 2f,
                        y = size.height * (scale - 1) / 2f
                    )
                    offset = Offset(
                        x = newOffset.x.coerceIn(-maxOffset.x, maxOffset.x),
                        y = newOffset.y.coerceIn(-maxOffset.y, maxOffset.y)
                    )
                }
            }
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
    ) {
        ByteArrayImage(
            modifier = Modifier
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                    translationX = offset.x
                    translationY = offset.y
                }
                .fillMaxSize()
                .padding(20.dp),
            content = imageContent,
            contentScale = ContentScale.Fit
        )
    }
}