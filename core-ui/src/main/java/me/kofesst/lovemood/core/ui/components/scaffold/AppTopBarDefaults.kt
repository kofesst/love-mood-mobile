package me.kofesst.lovemood.core.ui.components.scaffold

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

object AppTopBarDefaults {
    val smallContentPadding: PaddingValues
        @Composable get() = PaddingValues(20.dp)

    @Composable
    fun SmallBarLayout(
        modifier: Modifier = Modifier,
        horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(
            space = 20.dp
        ),
        verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
        content: @Composable RowScope.() -> Unit
    ) {
        Row(
            modifier = modifier,
            horizontalArrangement = horizontalArrangement,
            verticalAlignment = verticalAlignment,
            content = content
        )
    }

    @Composable
    fun smallBarColors(
        container: Color = MaterialTheme.colorScheme.background,
        content: Color = MaterialTheme.colorScheme.onBackground
    ) = AppTopBarColors(
        container = container,
        content = content
    )
}

data class AppTopBarColors(
    val container: Color,
    val content: Color
)