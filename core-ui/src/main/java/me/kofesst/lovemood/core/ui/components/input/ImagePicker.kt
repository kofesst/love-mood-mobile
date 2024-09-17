package me.kofesst.lovemood.core.ui.components.input

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.RotateLeft
import androidx.compose.material.icons.automirrored.rounded.RotateRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import kotlinx.coroutines.launch
import me.kofesst.lovemood.core.ui.components.action.PanelButton
import me.kofesst.lovemood.core.ui.components.image.ByteImage
import me.kofesst.lovemood.core.ui.utils.SELECT_IMAGE_LAUNCHER_INPUT
import me.kofesst.lovemood.core.ui.utils.rememberImagePickerLauncher
import me.kofesst.lovemood.core.ui.utils.rotate

internal const val DEFAULT_IMAGE_QUALITY = 80
internal const val DEFAULT_IMAGE_HEIGHT = 1024

@Composable
fun ImagePicker(
    modifier: Modifier = Modifier,
    imageContent: ByteArray,
    onContentChange: (ByteArray) -> Unit,
    containerColors: InputFieldContainerColors = InputFieldContainerDefaults.colors(),
    containerShape: Shape = InputFieldContainerDefaults.containerShape,
    imageHeight: Dp = 300.dp,
    imageShape: Shape = MaterialTheme.shapes.medium,
    outputImageQuality: Int = DEFAULT_IMAGE_QUALITY,
    outputImageHeight: Int = DEFAULT_IMAGE_HEIGHT,
    selectImageAction: @Composable () -> Unit = {},
    removeImageAction: @Composable () -> Unit = {}
) {
    val imagePickerLauncher = rememberImagePickerLauncher(
        context = LocalContext.current,
        outputQuality = outputImageQuality,
        outputHeight = outputImageHeight,
        onResult = onContentChange
    )

    fun selectImage() {
        imagePickerLauncher.launch(SELECT_IMAGE_LAUNCHER_INPUT)
    }

    InputFieldContainer(
        modifier = modifier,
        colors = containerColors,
        contentPadding = PaddingValues(all = 0.dp),
        containerShape = containerShape
    ) {
        AnimatedContent(
            modifier = Modifier.fillMaxWidth(),
            targetState = imageContent.isNotEmpty()
        ) { isImageLoaded ->
            val coroutineScope = rememberCoroutineScope()
            if (isImageLoaded) {
                ImagePickerContent(
                    modifier = Modifier.fillMaxWidth(),
                    imageContent = imageContent,
                    imageHeight = imageHeight,
                    imageShape = imageShape,
                    onSelectClick = { selectImage() },
                    selectImageAction = selectImageAction,
                    onRemoveClick = { onContentChange(byteArrayOf()) },
                    removeImageAction = removeImageAction,
                    onRotate = { rotation ->
                        coroutineScope.launch {
                            imageContent
                                .rotate(degrees = rotation.rightAngle)
                                .run(onContentChange)
                        }
                    }
                )
            } else {
                EmptyImagePickerContent(
                    modifier = Modifier.fillMaxWidth(),
                    onSelectClick = { selectImage() },
                    selectImageAction = selectImageAction
                )
            }
        }
    }
}

@Composable
private fun EmptyImagePickerContent(
    modifier: Modifier = Modifier,
    onSelectClick: () -> Unit,
    selectImageAction: @Composable () -> Unit
) {
    SelectImageButton(
        modifier = modifier,
        onClick = onSelectClick,
        content = selectImageAction
    )
}

internal enum class ImageRotation(val rightAngle: Float) {
    Clockwise(90f),
    CounterClockwise(-90f)
}

@Composable
private fun ImagePickerContent(
    modifier: Modifier = Modifier,
    imageContent: ByteArray,
    imageHeight: Dp,
    imageShape: Shape,
    onSelectClick: () -> Unit,
    selectImageAction: @Composable () -> Unit,
    onRemoveClick: () -> Unit,
    removeImageAction: @Composable () -> Unit,
    onRotate: (ImageRotation) -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(space = 10.dp)
    ) {
        ImageContent(
            modifier = Modifier
                .fillMaxWidth()
                .height(imageHeight)
                .clip(imageShape),
            content = imageContent,
            onRotateCounterClockwise = { onRotate(ImageRotation.CounterClockwise) },
            onRotateClockwise = { onRotate(ImageRotation.Clockwise) }
        )
        ImagePickerControls(
            modifier = Modifier.fillMaxWidth(),
            onSelectClick = onSelectClick,
            selectImageAction = selectImageAction,
            onRemoveClick = onRemoveClick,
            removeImageAction = removeImageAction
        )
    }
}

@Composable
private fun ImageContent(
    modifier: Modifier = Modifier,
    content: ByteArray,
    onRotateCounterClockwise: () -> Unit,
    onRotateClockwise: () -> Unit
) {
    Box(modifier) {
        ByteImage(
            modifier = Modifier.fillMaxSize(),
            byteContent = content
        )
        RotateButton(
            modifier = Modifier
                .zIndex(1f)
                .align(Alignment.BottomStart)
                .padding(10.dp),
            imageVector = Icons.AutoMirrored.Rounded.RotateLeft,
            onClick = onRotateCounterClockwise
        )
        RotateButton(
            modifier = Modifier
                .zIndex(1f)
                .align(Alignment.BottomEnd)
                .padding(10.dp),
            imageVector = Icons.AutoMirrored.Rounded.RotateRight,
            onClick = onRotateClockwise
        )
    }
}

@Composable
private fun RotateButton(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier,
        color = Color.Black.copy(alpha = 0.70f),
        contentColor = Color.White,
        shape = MaterialTheme.shapes.small,
        onClick = onClick
    ) {
        Icon(
            modifier = Modifier
                .padding(
                    horizontal = 20.dp,
                    vertical = 10.dp
                )
                .size(24.dp),
            imageVector = imageVector,
            contentDescription = null
        )
    }
}

@Composable
private fun ImagePickerControls(
    modifier: Modifier = Modifier,
    onSelectClick: () -> Unit,
    selectImageAction: @Composable () -> Unit,
    onRemoveClick: () -> Unit,
    removeImageAction: @Composable () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SelectImageButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = onSelectClick,
            content = selectImageAction
        )
        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 10.dp),
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.50f)
        )
        RemoveImageButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = onRemoveClick,
            content = removeImageAction
        )
    }
}

@Composable
private fun SelectImageButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    PanelButton(
        modifier = modifier,
        onClick = onClick
    ) {
        CompositionLocalProvider(
            LocalContentColor provides MaterialTheme.colorScheme.primary,
            LocalTextStyle provides MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )
        ) {
            content()
        }
    }
}

@Composable
private fun RemoveImageButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    PanelButton(
        modifier = modifier,
        onClick = onClick
    ) {
        CompositionLocalProvider(
            LocalContentColor provides MaterialTheme.colorScheme.error,
            LocalTextStyle provides MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )
        ) {
            content()
        }
    }
}