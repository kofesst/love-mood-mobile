package me.kofesst.lovemood.core.ui.components.input

import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.RotateLeft
import androidx.compose.material.icons.automirrored.outlined.RotateRight
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.kofesst.lovemood.core.ui.components.action.PanelButton
import me.kofesst.lovemood.core.ui.components.action.PanelButtonDefaults
import me.kofesst.lovemood.core.ui.components.image.ByteImage
import me.kofesst.lovemood.core.ui.transitions.fadeTransition
import me.kofesst.lovemood.core.ui.utils.SELECT_IMAGE_LAUNCHER_INPUT
import me.kofesst.lovemood.core.ui.utils.compress
import me.kofesst.lovemood.core.ui.utils.getImagePickerLauncher
import me.kofesst.lovemood.core.ui.utils.rotate

private const val SMALL_IMAGE_COMPRESS_QUALITY = 75
private const val SMALL_IMAGE_COMPRESS_HEIGHT = 512

private const val LARGE_IMAGE_COMPRESS_QUALITY = 100
private const val LARGE_IMAGE_COMPRESS_HEIGHT = 1024

@Composable
fun LargeImagePickerField(
    modifier: Modifier = Modifier,
    defaults: InputFieldContainerDefaults = InputFieldContainerDefaults.defaults(),
    loadedContent: ByteArray,
    onContentLoad: (ByteArray) -> Unit,
    coroutineScope: CoroutineScope,
    label: String,
    action: String,
    previewHeight: Dp = 400.dp
) {
    ImagePickerFieldContainer(
        modifier = modifier,
        defaults = defaults.copy(
            contentPadding = PaddingValues(0.dp)
        ),
        loadedContent = loadedContent,
        onContentLoad = onContentLoad,
        coroutineScope = coroutineScope,
        label = label,
        action = action,
        pictureCompressQuality = LARGE_IMAGE_COMPRESS_QUALITY,
        pictureCompressHeight = LARGE_IMAGE_COMPRESS_HEIGHT
    ) { imagePicker ->
        LargeImageLoadedContent(
            modifier = Modifier.fillMaxWidth(),
            content = loadedContent,
            onSelectAnotherClick = { imagePicker.launch(SELECT_IMAGE_LAUNCHER_INPUT) },
            onContentChange = onContentLoad,
            coroutineScope = coroutineScope,
            previewHeight = previewHeight
        )
    }
}

@Composable
fun SmallImagePickerField(
    modifier: Modifier = Modifier,
    defaults: InputFieldContainerDefaults = InputFieldContainerDefaults.defaults(),
    loadedContent: ByteArray,
    onContentLoad: (ByteArray) -> Unit,
    coroutineScope: CoroutineScope,
    label: String,
    action: String
) {
    ImagePickerFieldContainer(
        modifier = modifier,
        defaults = defaults,
        loadedContent = loadedContent,
        onContentLoad = onContentLoad,
        coroutineScope = coroutineScope,
        label = label,
        action = action,
        pictureCompressQuality = SMALL_IMAGE_COMPRESS_QUALITY,
        pictureCompressHeight = SMALL_IMAGE_COMPRESS_HEIGHT
    ) { imagePicker ->
        SmallImageLoadedContent(
            modifier = Modifier.fillMaxWidth(),
            content = loadedContent,
            onSelectAnotherClick = { imagePicker.launch(SELECT_IMAGE_LAUNCHER_INPUT) },
            onContentChange = onContentLoad,
            coroutineScope = coroutineScope
        )
    }
}

@Composable
private fun ImagePickerFieldContainer(
    modifier: Modifier = Modifier,
    defaults: InputFieldContainerDefaults = InputFieldContainerDefaults.defaults(),
    loadedContent: ByteArray,
    onContentLoad: (ByteArray) -> Unit,
    coroutineScope: CoroutineScope,
    label: String,
    action: String,
    pictureCompressQuality: Int,
    pictureCompressHeight: Int,
    pickedPictureContent: @Composable (imagePicker: ManagedActivityResultLauncher<String, Uri?>) -> Unit
) {
    BaseInputFieldContainer(
        modifier = modifier,
        defaults = defaults.copy(
            contentPadding = PaddingValues(0.dp)
        )
    ) {
        val context = LocalContext.current
        val imagePicker = getImagePickerLauncher { uri ->
            if (uri == null) {
                return@getImagePickerLauncher
            }
            val item = context.contentResolver.openInputStream(uri)
            if (item != null) {
                coroutineScope.launch {
                    val bytes = item.readBytes().compress(
                        quality = pictureCompressQuality,
                        newHeight = pictureCompressHeight
                    )
                    onContentLoad(bytes)
                    item.close()
                }
            }
        }
        AnimatedContent(
            modifier = Modifier.fillMaxWidth(),
            targetState = loadedContent.isNotEmpty(),
            label = "image_picker_content",
            transitionSpec = { fadeTransition }
        ) { isPicturePicked ->
            if (isPicturePicked) {
                pickedPictureContent(imagePicker)
            } else {
                ImageNotLoadedContent(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    label = label,
                    action = action,
                    onSelectImageClick = { imagePicker.launch(SELECT_IMAGE_LAUNCHER_INPUT) }
                )
            }
        }
    }
}

@Composable
private fun ImageNotLoadedContent(
    modifier: Modifier = Modifier,
    label: String,
    action: String,
    onSelectImageClick: () -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = label,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center
        )
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onSelectImageClick
        ) {
            Text(
                text = action,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
private fun LargeImageLoadedContent(
    modifier: Modifier = Modifier,
    content: ByteArray,
    onSelectAnotherClick: () -> Unit,
    onContentChange: (ByteArray) -> Unit,
    coroutineScope: CoroutineScope,
    previewHeight: Dp
) {
    ImageLoadedContent(
        modifier = modifier,
        content = content,
        onSelectAnotherClick = onSelectAnotherClick,
        onContentChange = onContentChange,
        coroutineScope = coroutineScope
    ) {
        LargeImagePreview(
            modifier = Modifier
                .fillMaxWidth()
                .height(previewHeight),
            imageContent = content
        )
    }
}

@Composable
private fun SmallImageLoadedContent(
    modifier: Modifier = Modifier,
    content: ByteArray,
    onSelectAnotherClick: () -> Unit,
    onContentChange: (ByteArray) -> Unit,
    coroutineScope: CoroutineScope
) {
    ImageLoadedContent(
        modifier = modifier,
        content = content,
        onSelectAnotherClick = onSelectAnotherClick,
        onContentChange = onContentChange,
        coroutineScope = coroutineScope
    ) {
        SmallImagePreview(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            imageContent = content
        )
    }
}

@Composable
private fun ImageLoadedContent(
    modifier: Modifier = Modifier,
    content: ByteArray,
    onSelectAnotherClick: () -> Unit,
    onContentChange: (ByteArray) -> Unit,
    coroutineScope: CoroutineScope,
    previewContent: @Composable () -> Unit
) {
    Column(modifier) {
        previewContent()
        ImageEditControls(
            modifier = Modifier.fillMaxWidth(),
            onSelectAnotherClick = onSelectAnotherClick,
            onRotateClockwiseClick = {
                coroutineScope.launch(Dispatchers.IO) {
                    val rotatedContent = content.rotate(degrees = 90f)
                    onContentChange(rotatedContent)
                }
            },
            onRotateCounterClockwiseClick = {
                coroutineScope.launch(Dispatchers.IO) {
                    val rotatedContent = content.rotate(degrees = -90f)
                    onContentChange(rotatedContent)
                }
            },
            onDeleteClick = {
                onContentChange(byteArrayOf())
            }
        )
    }
}

@Composable
private fun LargeImagePreview(
    modifier: Modifier = Modifier,
    imageContent: ByteArray
) {
    ImagePreviewContainer(
        modifier = modifier,
        imageContent = imageContent
    ) {
        ByteImage(
            modifier = Modifier.fillMaxSize(),
            byteContent = imageContent
        )
    }
}

@Composable
private fun SmallImagePreview(
    modifier: Modifier = Modifier,
    imageContent: ByteArray
) {
    ImagePreviewContainer(
        modifier = modifier,
        imageContent = imageContent
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            ByteImage(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(128.dp)
                    .border(
                        width = 5.dp,
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = CircleShape
                    ),
                byteContent = imageContent
            )
            ByteImage(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(96.dp)
                    .border(
                        width = 5.dp,
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = CircleShape
                    ),
                byteContent = imageContent
            )
            ByteImage(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(72.dp)
                    .border(
                        width = 5.dp,
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = CircleShape
                    ),
                byteContent = imageContent
            )
        }
    }
}

@Composable
private fun ImagePreviewContainer(
    modifier: Modifier = Modifier,
    imageContent: ByteArray,
    onLoadedContent: @Composable () -> Unit
) {
    AnimatedContent(
        modifier = modifier,
        targetState = imageContent.isNotEmpty(),
        label = "image_preview",
        transitionSpec = { fadeTransition }
    ) { isPictureLoaded ->
        if (isPictureLoaded) {
            onLoadedContent()
        }
    }
}

@Composable
private fun ImageEditControls(
    modifier: Modifier = Modifier,
    onSelectAnotherClick: () -> Unit,
    onRotateClockwiseClick: () -> Unit,
    onRotateCounterClockwiseClick: () -> Unit,
    onDeleteClick: () -> Unit,
    buttonDefaults: PanelButtonDefaults = PanelButtonDefaults.defaults(
        textColor = MaterialTheme.colorScheme.onPrimaryContainer
    )
) {
    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            PanelButton(
                modifier = Modifier.fillMaxWidth(),
                defaults = buttonDefaults,
                icon = Icons.Outlined.Image,
                action = "Выбрать другое изображение",
                onClick = onSelectAnotherClick
            )
            HorizontalDivider(
                modifier = Modifier.padding(start = 54.dp),
                color = LocalContentColor.current.copy(alpha = 0.5f)
            )
            PanelButton(
                modifier = Modifier.fillMaxWidth(),
                defaults = buttonDefaults,
                icon = Icons.AutoMirrored.Outlined.RotateRight,
                action = "Повернуть по часовой стрелке",
                onClick = onRotateClockwiseClick
            )
            HorizontalDivider(
                modifier = Modifier.padding(start = 54.dp),
                color = LocalContentColor.current.copy(alpha = 0.5f)
            )
            PanelButton(
                modifier = Modifier.fillMaxWidth(),
                defaults = buttonDefaults,
                icon = Icons.AutoMirrored.Outlined.RotateLeft,
                action = "Повернуть против часовой стрелки",
                onClick = onRotateCounterClockwiseClick
            )
            HorizontalDivider(
                modifier = Modifier.padding(start = 54.dp),
                color = LocalContentColor.current.copy(alpha = 0.5f)
            )
            PanelButton(
                modifier = Modifier.fillMaxWidth(),
                defaults = buttonDefaults,
                icon = Icons.Outlined.Delete,
                action = "Удалить изображение",
                onClick = onDeleteClick
            )
        }
    }
}