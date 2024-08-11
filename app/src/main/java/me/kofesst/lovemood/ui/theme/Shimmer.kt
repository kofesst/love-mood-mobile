package me.kofesst.lovemood.ui.theme

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import com.valentinilk.shimmer.LocalShimmerTheme
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.defaultShimmerTheme
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmerSpec
import me.kofesst.lovemood.presentation.app.LocalShimmer

private fun lightShimmerColors() = listOf(
    Color.Unspecified.copy(alpha = 0.00f),
    Color.Unspecified.copy(alpha = 0.20f),
    Color.Unspecified.copy(alpha = 0.00f)
)

private fun darkShimmerColors() = listOf(
    Color.Unspecified.copy(alpha = 0.45f),
    Color.Unspecified.copy(alpha = 1.00f),
    Color.Unspecified.copy(alpha = 0.45f)
)

private val baseShimmerTheme = defaultShimmerTheme.copy(
    animationSpec = infiniteRepeatable(
        animation = shimmerSpec(
            durationMillis = 800,
            easing = LinearOutSlowInEasing,
            delayMillis = 400,
        ),
        repeatMode = RepeatMode.Restart,
    )
)
val LightShimmerTheme = baseShimmerTheme.copy(
    shaderColors = lightShimmerColors()
)
val DarkShimmerTheme = baseShimmerTheme.copy(
    shaderColors = darkShimmerColors()
)

@Composable
fun WithShimmerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val shimmerTheme = when {
        darkTheme -> DarkShimmerTheme
        else -> LightShimmerTheme
    }
    CompositionLocalProvider(
        LocalShimmerTheme provides shimmerTheme
    ) {
        content()
    }
}

@Composable
fun WithShimmerEffect(
    content: @Composable () -> Unit
) {
    val shimmerTheme = LocalShimmerTheme.current
    val shimmer = rememberShimmer(
        shimmerBounds = ShimmerBounds.Window,
        theme = shimmerTheme
    )
    CompositionLocalProvider(
        LocalShimmer provides shimmer
    ) {
        content()
    }
}