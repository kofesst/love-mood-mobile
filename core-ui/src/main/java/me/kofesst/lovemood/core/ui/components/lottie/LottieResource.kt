package me.kofesst.lovemood.core.ui.components.lottie

import androidx.annotation.RawRes

data class LottieResource(
    @RawRes val rawResId: Int,
    val speedMultiplier: Float = 1.0f,
    val repeatMode: RepeatMode = RepeatMode()
) {
    data class RepeatMode(
        val count: Count = Count.Once,
        val behavior: Behavior = Behavior.Restart
    ) {
        enum class Count {
            Once, Infinite
        }

        enum class Behavior {
            Restart, Reverse
        }
    }
}
