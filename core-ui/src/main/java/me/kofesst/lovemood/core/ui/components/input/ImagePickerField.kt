package me.kofesst.lovemood.core.ui.components.input

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.kofesst.lovemood.core.ui.components.action.PanelButton
import me.kofesst.lovemood.core.ui.components.action.PanelButtonDefaults
import me.kofesst.lovemood.core.ui.transitions.fadeTransition
import me.kofesst.lovemood.core.ui.utils.ByteArrayImage
import me.kofesst.lovemood.core.ui.utils.SELECT_IMAGE_LAUNCHER_INPUT
import me.kofesst.lovemood.core.ui.utils.compress
import me.kofesst.lovemood.core.ui.utils.getImagePickerLauncher
import me.kofesst.lovemood.core.ui.utils.rotate

@Preview
@Composable
private fun ImagePickerFieldPreview() {
    SmallImagePickerField(
        modifier = Modifier.width(400.dp),
        loadedContent = byteArrayOf(),
        onContentLoad = {},
        coroutineScope = rememberCoroutineScope(),
        label = "Load your profile picture",
        action = "Choose image"
    )
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
                    val bytes = item.readBytes().compress()
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
                ImageLoadedContent(
                    modifier = Modifier.fillMaxWidth(),
                    content = loadedContent,
                    onSelectAnotherClick = { imagePicker.launch(SELECT_IMAGE_LAUNCHER_INPUT) },
                    onContentChange = onContentLoad,
                    coroutineScope = coroutineScope
                )
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
fun ImageLoadedContent(
    modifier: Modifier = Modifier,
    content: ByteArray,
    onSelectAnotherClick: () -> Unit,
    onContentChange: (ByteArray) -> Unit,
    coroutineScope: CoroutineScope
) {
    Column(modifier) {
        ImagePreview(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            content = content
        )
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
private fun ImagePreview(
    modifier: Modifier = Modifier,
    content: ByteArray
) {
    AnimatedContent(
        modifier = modifier,
        targetState = content.isNotEmpty(),
        label = "image_preview",
        transitionSpec = { fadeTransition }
    ) { isPictureLoaded ->
        if (isPictureLoaded) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                ByteArrayImage(
                    modifier = Modifier.border(
                        width = 5.dp,
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = CircleShape
                    ),
                    content = content,
                    size = 128.dp,
                    shape = CircleShape
                )
                ByteArrayImage(
                    modifier = Modifier.border(
                        width = 5.dp,
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = CircleShape
                    ),
                    content = content,
                    size = 96.dp,
                    shape = CircleShape
                )
                ByteArrayImage(
                    modifier = Modifier.border(
                        width = 5.dp,
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = CircleShape
                    ),
                    content = content,
                    size = 72.dp,
                    shape = CircleShape
                )
            }
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