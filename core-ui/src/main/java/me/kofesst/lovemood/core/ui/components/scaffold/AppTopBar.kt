package me.kofesst.lovemood.core.ui.components.scaffold

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import me.kofesst.lovemood.core.ui.utils.alsoStatusBar

@Composable
fun SmallAppTopBar(
    modifier: Modifier = Modifier,
    colors: AppTopBarColors = AppTopBarDefaults.smallBarColors(),
    contentPadding: PaddingValues = AppTopBarDefaults.smallContentPadding,
    leftContent: @Composable () -> Unit = {},
    mainContent: @Composable () -> Unit = {},
    rightContent: @Composable () -> Unit = {}
) {
    Surface(
        modifier = modifier,
        color = colors.container,
        contentColor = colors.content
    ) {
        AppTopBarDefaults.SmallBarLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(contentPadding.alsoStatusBar())
        ) {
            leftContent()
            mainContent()
            rightContent()
        }
    }
}