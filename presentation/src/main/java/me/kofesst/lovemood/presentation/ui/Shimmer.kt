package me.kofesst.lovemood.presentation.ui

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.ShimmerTheme
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer
import com.valentinilk.shimmer.shimmerSpec

@Composable
fun Modifier.shimmer(
    baseColor: Color = LocalContentColor.current,
    bounds: ShimmerBounds = ShimmerBounds.Window
): Modifier {
    val shimmer = rememberShimmer(
        shimmerBounds = bounds,
        theme = getShimmerTheme(baseColor)
    )
    return then(
        Modifier.shimmer(customShimmer = shimmer)
    )
}

private fun getShimmerTheme(baseColor: Color): ShimmerTheme {
    return ShimmerTheme(
        animationSpec = infiniteRepeatable(
            animation = shimmerSpec(
                durationMillis = 800,
                easing = LinearOutSlowInEasing,
                delayMillis = 400,
            ),
            repeatMode = RepeatMode.Restart,
        ),
        blendMode = BlendMode.DstIn,
        shimmerWidth = 400.dp,
        rotation = 15f,
        shaderColors = listOf(
            baseColor.copy(alpha = 0.10f),
            baseColor.copy(alpha = 0.25f),
            baseColor.copy(alpha = 0.40f),
            baseColor.copy(alpha = 0.25f),
            baseColor.copy(alpha = 0.10f)
        ),
        shaderColorStops = listOf(
            0.00f,
            0.25f,
            0.50f,
            0.75f,
            1.00f
        )
    )
}