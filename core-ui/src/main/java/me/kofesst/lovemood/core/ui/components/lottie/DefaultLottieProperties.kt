package me.kofesst.lovemood.core.ui.components.lottie

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import com.airbnb.lottie.LottieProperty
import com.airbnb.lottie.compose.LottieDynamicProperty
import com.airbnb.lottie.compose.rememberLottieDynamicProperty

object DefaultLottieProperties {
    @Composable
    fun partColorProperty(keyPath: Array<String>, composeColor: Color): LottieDynamicProperty<*> {
        return rememberLottieDynamicProperty(
            property = LottieProperty.COLOR_FILTER,
            value = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
                composeColor.hashCode(), BlendModeCompat.SRC_ATOP
            ),
            keyPath = keyPath
        )
    }
}