package me.kofesst.lovemood.core.ui.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
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