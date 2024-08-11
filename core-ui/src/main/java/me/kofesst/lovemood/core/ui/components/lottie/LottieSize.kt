package me.kofesst.lovemood.core.ui.components.lottie

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

sealed class LottieSize {
    data class Fixed(val dp: Dp) : LottieSize()
    data object Maximized : LottieSize()
}

fun Modifier.lottieSize(size: LottieSize) = then(
    when (size) {
        is LottieSize.Fixed -> Modifier.size(size.dp)
        is LottieSize.Maximized -> Modifier.fillMaxSize()
    }
)