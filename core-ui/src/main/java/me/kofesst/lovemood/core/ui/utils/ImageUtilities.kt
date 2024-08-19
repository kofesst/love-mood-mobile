package me.kofesst.lovemood.core.ui.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream

/**
 * Параметр для выбора изображения.
 */
const val SELECT_IMAGE_LAUNCHER_INPUT = "image/*"

/**
 * Возвращает лаунчер выбора изображения.
 *
 * [onResult] - callback-функция, вызываемая при
 * успешном выборе фотографии пользователем.
 */
@Composable
fun getImagePickerLauncher(onResult: (Uri?) -> Unit) = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.GetContent(),
    onResult = onResult
)

/**
 * Стандартные настройки для Bitmap.
 */
private val defaultBitmapOptions = BitmapFactory.Options().apply {
    inPreferredConfig = Bitmap.Config.ARGB_8888
    inMutable = true
}

/**
 * Сжимает контент изображения ([ByteArray]), до оптимального
 * размера и веса.
 *
 * [quality] - качество исходного изображения.
 *
 * [newHeight] - высота исходного изображения.
 */
suspend fun ByteArray.compress(quality: Int = 75, newHeight: Int = 512): ByteArray {
    return withContext(Dispatchers.IO) {
        val decoded = BitmapFactory.decodeByteArray(this@compress, 0, size, defaultBitmapOptions)
        val ratio = decoded.height.toFloat() / decoded.width.toFloat()
        val rotatedBitmap = Bitmap.createScaledBitmap(
            decoded,
            (newHeight / ratio).toInt(),
            newHeight,
            false
        )
        val outputStream = ByteArrayOutputStream()
        rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
        if (rotatedBitmap != decoded) {
            rotatedBitmap.recycle()
        }
        decoded.recycle()
        val normalizedBytes = outputStream.toByteArray()
        outputStream.close()
        return@withContext normalizedBytes
    }
}

/**
 * Конвертирует набор байтов в изображение [Bitmap].
 */
suspend fun ByteArray.asBitmap(): Bitmap? {
    return withContext(Dispatchers.IO) {
        val decodedBitmap = BitmapFactory.decodeByteArray(
            this@asBitmap,
            0,
            size,
            defaultBitmapOptions
        )
        return@withContext decodedBitmap
    }
}

/**
 * Поворачивает изображение на [degrees] градусов.
 */
suspend fun ByteArray.rotate(degrees: Float): ByteArray {
    return withContext(Dispatchers.IO) {
        val src = BitmapFactory.decodeByteArray(this@rotate, 0, size, defaultBitmapOptions)
        val matrix = Matrix()
        matrix.postRotate(degrees)
        val rotatedBitmap = Bitmap.createBitmap(src, 0, 0, src.width, src.height, matrix, false)
        val outputStream = ByteArrayOutputStream()
        rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        val rotatedBytes = outputStream.toByteArray()
        outputStream.close()
        rotatedBytes
    }
}

/**
 * Круглое изображение, получаемое из набора байтов.
 *
 * [modifier] - модификатор изображения.
 *
 * [content] - набор байтов изображения.
 *
 * [size] - размер изображения.
 *
 * [shape] - форма изображения.
 *
 * [placeholder] - контент, появляющийся во время загрузки изображения.
 */
@Composable
fun ByteArrayImage(
    modifier: Modifier = Modifier,
    content: ByteArray,
    size: Dp = 96.dp,
    shape: Shape = CircleShape,
    placeholder: @Composable () -> Unit = {
        CircularProgressIndicator(
            modifier = Modifier.size(size)
        )
    },
    colorFilter: ColorFilter? = null
) {
    ByteArrayImage(
        modifier = modifier
            .clip(shape)
            .size(size),
        content = content,
        placeholder = placeholder,
        colorFilter = colorFilter
    )
}

@Composable
fun rememberBitmap(content: ByteArray): Bitmap? {
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    LaunchedEffect(content) { bitmap = content.asBitmap() }
    return bitmap
}

/**
 * Круглое изображение, получаемое из набора байтов.
 *
 * [modifier] - модификатор изображения.
 *
 * [content] - набор байтов изображения.
 *
 * [placeholder] - контент, появляющийся во время загрузки изображения.
 *
 * [contentScale] - параметры масштабирования изображения.
 */
@Composable
fun ByteArrayImage(
    modifier: Modifier = Modifier,
    content: ByteArray,
    placeholder: @Composable () -> Unit = {},
    colorFilter: ColorFilter? = null,
    contentScale: ContentScale = ContentScale.Crop
) {
    val pictureBitmap = rememberBitmap(content)
    Crossfade(
        targetState = content.isNotEmpty(),
        label = "byte_array_image"
    ) { isContentLoaded ->
        if (!isContentLoaded) {
            placeholder()
        } else {
            Crossfade(
                targetState = pictureBitmap,
                label = "image_content"
            ) { bitmap ->
                if (bitmap == null) {
                    placeholder()
                } else {
                    Image(
                        modifier = modifier,
                        bitmap = bitmap.asImageBitmap(),
                        contentDescription = null,
                        contentScale = contentScale,
                        colorFilter = colorFilter
                    )
                }
            }
        }
    }
}