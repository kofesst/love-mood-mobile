package me.kofesst.lovemood.core.ui.components.input

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import me.kofesst.lovemood.core.ui.components.action.PanelButton
import me.kofesst.lovemood.core.ui.components.image.ByteImage
import me.kofesst.lovemood.core.ui.utils.SELECT_IMAGE_LAUNCHER_INPUT
import me.kofesst.lovemood.core.ui.utils.rememberImagePickerLauncher

const val DEFAULT_IMAGE_QUALITY = 80
const val DEFAULT_IMAGE_HEIGHT = 1024

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
            if (isImageLoaded) {
                ImagePickerContent(
                    modifier = Modifier.fillMaxWidth(),
                    imageContent = imageContent,
                    imageHeight = imageHeight,
                    imageShape = imageShape,
                    onSelectClick = { selectImage() },
                    selectImageAction = selectImageAction,
                    onRemoveClick = { onContentChange(byteArrayOf()) },
                    removeImageAction = removeImageAction
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

@Composable
private fun ImagePickerContent(
    modifier: Modifier = Modifier,
    imageContent: ByteArray,
    imageHeight: Dp,
    imageShape: Shape,
    onSelectClick: () -> Unit,
    selectImageAction: @Composable () -> Unit,
    onRemoveClick: () -> Unit,
    removeImageAction: @Composable () -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(space = 10.dp)
    ) {
        ByteImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(imageHeight)
                .clip(imageShape),
            byteContent = imageContent
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