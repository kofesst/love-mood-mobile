package me.kofesst.lovemood.core.ui.components.lottie

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.LottieDynamicProperty
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.airbnb.lottie.compose.rememberLottieDynamicProperties

@Composable
fun LottieFile(
    modifier: Modifier = Modifier,
    resource: LottieResource,
    size: LottieSize = LottieSize.Maximized,
    vararg dynamicProperties: LottieDynamicProperty<*>
) {
    val lottieSpec = remember(resource) { LottieCompositionSpec.RawRes(resId = resource.rawResId) }
    val lottieComposition by rememberLottieComposition(spec = lottieSpec)
    val lottieProgress by animateLottieCompositionAsState(
        composition = lottieComposition,
        reverseOnRepeat = resource.repeatMode.behavior == LottieResource.RepeatMode.Behavior.Reverse,
        iterations = when (resource.repeatMode.count) {
            LottieResource.RepeatMode.Count.Once -> 1
            LottieResource.RepeatMode.Count.Infinite -> LottieConstants.IterateForever
        }
    )
    val lottieDynamicProperties = rememberLottieDynamicProperties(*dynamicProperties)
    LottieAnimation(
        modifier = modifier.lottieSize(size),
        composition = lottieComposition,
        progress = { lottieProgress },
        dynamicProperties = lottieDynamicProperties
    )
}