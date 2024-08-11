package me.kofesst.lovemood.ui

import androidx.compose.runtime.Composable
import me.kofesst.lovemood.R
import me.kofesst.lovemood.core.models.Gender
import me.kofesst.lovemood.core.ui.components.lottie.DefaultLottieProperties
import me.kofesst.lovemood.core.ui.components.lottie.LottieResource
import me.kofesst.lovemood.ui.theme.containerColor

object LottieResources {
    object SadHeart {
        fun resource() = LottieResource(
            rawResId = R.raw.lottie__sad_heart,
            speedMultiplier = 1.0f,
            repeatMode = LottieResource.RepeatMode(
                count = LottieResource.RepeatMode.Count.Infinite,
                behavior = LottieResource.RepeatMode.Behavior.Restart
            )
        )

        @Composable
        fun dynamicProperties(gender: Gender) = arrayOf(
            DefaultLottieProperties.partColorProperty(
                keyPath = arrayOf("lottie_layer", "sad_heart", "body", "body", "Fill"),
                composeColor = gender.containerColor
            )
        )
    }

    object ChickBuilder {
        fun resource() = LottieResource(
            rawResId = R.raw.lottie__chick_builder,
            speedMultiplier = 2.0f,
            repeatMode = LottieResource.RepeatMode(
                count = LottieResource.RepeatMode.Count.Infinite,
                behavior = LottieResource.RepeatMode.Behavior.Restart
            )
        )
    }
}