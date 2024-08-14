package me.kofesst.lovemood.core.ui.components.scaffold

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import me.kofesst.lovemood.core.ui.utils.mergeWith
import me.kofesst.lovemood.core.ui.utils.navigationBarPadding

object AppBottomBarDefaults {
    val iconSize: Dp
        @Composable get() = 36.dp

    val titleStyle: TextStyle
        @Composable get() = MaterialTheme.typography.bodyMedium.copy(
            fontWeight = FontWeight.Bold
        )

    @Composable
    fun layout(
        containerPadding: PaddingValues = PaddingValues(
            horizontal = 24.dp,
            vertical = 16.dp
        ) mergeWith navigationBarPadding(),
        containerHorizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(
            space = 20.dp,
            alignment = Alignment.Start
        ),
        containerVerticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
        itemPadding: PaddingValues = PaddingValues(
            horizontal = 16.dp,
            vertical = 8.dp
        ),
        itemHorizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(
            space = 8.dp,
            alignment = Alignment.Start
        ),
        itemVerticalAlignment: Alignment.Vertical = Alignment.CenterVertically
    ) = AppBottomBarLayout(
        containerPadding = containerPadding,
        containerHorizontalArrangement = containerHorizontalArrangement,
        containerVerticalAlignment = containerVerticalAlignment,
        itemPadding = itemPadding,
        itemHorizontalArrangement = itemHorizontalArrangement,
        itemVerticalAlignment = itemVerticalAlignment
    )

    @Composable
    fun colors(
        tonalElevation: Dp = 6.dp,
        shadowElevation: Dp = 6.dp,
        container: Color = MaterialTheme.colorScheme.surfaceVariant,
        itemContainer: Color = MaterialTheme.colorScheme.surfaceVariant,
        icon: Color = MaterialTheme.colorScheme.onSurfaceVariant,
        selectedItemContainer: Color = MaterialTheme.colorScheme.primaryContainer,
        selectedIcon: Color = MaterialTheme.colorScheme.onPrimaryContainer,
        selectedText: Color = MaterialTheme.colorScheme.onPrimaryContainer
    ) = AppBottomBarColors(
        tonalElevation = tonalElevation,
        shadowElevation = shadowElevation,
        container = container,
        itemContainer = itemContainer,
        icon = icon,
        selectedItemContainer = selectedItemContainer,
        selectedIcon = selectedIcon,
        selectedText = selectedText
    )

    @Composable
    fun shapes(
        container: Shape = RoundedCornerShape(20.dp),
        item: Shape = RoundedCornerShape(20.dp)
    ) = AppBottomBarShapes(
        container = container,
        item = item
    )
}

class AppBottomBarLayout(
    val containerPadding: PaddingValues,
    val containerHorizontalArrangement: Arrangement.Horizontal,
    val containerVerticalAlignment: Alignment.Vertical,
    val itemPadding: PaddingValues,
    val itemHorizontalArrangement: Arrangement.Horizontal,
    val itemVerticalAlignment: Alignment.Vertical
)

class AppBottomBarColors(
    val tonalElevation: Dp,
    val shadowElevation: Dp,
    val container: Color,
    val itemContainer: Color,
    val icon: Color,
    val selectedItemContainer: Color,
    val selectedIcon: Color,
    val selectedText: Color
)

class AppBottomBarShapes(
    val container: Shape,
    val item: Shape
)