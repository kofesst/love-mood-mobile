package me.kofesst.lovemood.presentation.screens.home.sections

import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.shimmer
import me.kofesst.lovemood.core.models.Relationship
import me.kofesst.lovemood.presentation.app.LocalShimmer
import me.kofesst.lovemood.ui.theme.WithShimmerEffect

object ShimmerSection : HomeScreenSection() {
    override val containerModifier: Modifier
        @Composable get() = Modifier
            .height(150.dp)
            .shimmer(LocalShimmer.current)

    @Composable
    override fun ContainerWrapper(modifier: Modifier, content: @Composable () -> Unit) {
        WithShimmerEffect(content)
    }

    override val bodyContent: @Composable (Modifier, Relationship?) -> Unit
        get() = { _, _ -> }
}