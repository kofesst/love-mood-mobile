package me.kofesst.lovemood.ui

import me.kofesst.lovemood.R
import me.kofesst.lovemood.core.ui.components.lottie.LottieResource

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