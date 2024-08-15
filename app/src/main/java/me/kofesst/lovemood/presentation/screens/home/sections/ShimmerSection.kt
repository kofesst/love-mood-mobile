package me.kofesst.lovemood.presentation.screens.home.sections

import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.shimmer
import me.kofesst.lovemood.core.ui.components.cards.BaseCard
import me.kofesst.lovemood.presentation.app.LocalShimmer
import me.kofesst.lovemood.ui.theme.WithShimmerEffect

@Composable
fun ShimmerSection(
    modifier: Modifier = Modifier
) {
    WithShimmerEffect {
        BaseCard(
            modifier = modifier
                .height(150.dp)
                .shimmer(LocalShimmer.current),
            backgroundImagePainter = null
        ) {}
    }
}