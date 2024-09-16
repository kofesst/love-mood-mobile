package me.kofesst.lovemood.core.ui.components.image

import android.graphics.Bitmap
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import me.kofesst.lovemood.core.ui.utils.asBitmap

/**
 * Запоминает и возвращает Bitmap из
 * массива байтов [byteContent].
 */
@Composable
fun rememberBitmap(byteContent: ByteArray): Bitmap? {
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    LaunchedEffect(byteContent) { bitmap = byteContent.asBitmap() }
    return bitmap
}

/**
 * Компонент изображения, контент которого
 * представлен в виде массива байтов.
 *
 * [modifier] - модификатор контейнера компонента.
 *
 * [byteContent] - контент изображения в виде массива байтов.
 *
 * [contentDescription] - описание изображения.
 *
 * [placeholder] - плейсхолдер изображения, показывающийся
 * когда изображение не загружено или не может быть загружено.
 *
 * [contentScale] - масштаб изображения.
 *
 * [colorFilter] - фильтр изображения.
 */
@Composable
fun ByteImage(
    modifier: Modifier = Modifier,
    byteContent: ByteArray,
    contentDescription: String? = null,
    placeholder: @Composable () -> Unit = {},
    contentScale: ContentScale = ContentScale.Crop,
    colorFilter: ColorFilter? = null
) {
    val bitmap = rememberBitmap(byteContent)
    Crossfade(
        modifier = modifier,
        targetState = byteContent.isNotEmpty() && bitmap != null,
        label = "byte_image"
    ) { showBitmap ->
        if (showBitmap) {
            Image(
                modifier = Modifier.fillMaxSize(),
                bitmap = bitmap!!.asImageBitmap(),
                contentDescription = contentDescription,
                contentScale = contentScale,
                colorFilter = colorFilter
            )
        } else {
            placeholder()
        }
    }
}