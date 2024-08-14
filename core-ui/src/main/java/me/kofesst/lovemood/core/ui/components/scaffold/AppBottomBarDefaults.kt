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
        layoutPadding: PaddingValues = PaddingValues(
            start = 20.dp,
            end = 20.dp,
            top = 0.dp,
            bottom = 10.dp
        ) mergeWith navigationBarPadding(),
        containerPadding: PaddingValues = PaddingValues(
            horizontal = 16.dp,
            vertical = 8.dp
        ),
        containerHorizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(8.dp),
        containerVerticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
        itemPadding: PaddingValues = PaddingValues(
            horizontal = 8.dp,
            vertical = 4.dp
        ),
        itemHorizontalAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
        itemVerticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(4.dp)
    ) = AppBottomBarLayout(
        layoutPadding = layoutPadding,
        containerPadding = containerPadding,
        containerHorizontalArrangement = containerHorizontalArrangement,
        containerVerticalAlignment = containerVerticalAlignment,
        itemPadding = itemPadding,
        itemHorizontalAlignment = itemHorizontalAlignment,
        itemVerticalArrangement = itemVerticalArrangement
    )

    @Composable
    fun colors(
        tonalElevation: Dp = 3.dp,
        shadowElevation: Dp = 3.dp,
        container: Color = MaterialTheme.colorScheme.surfaceVariant,
        icon: Color = MaterialTheme.colorScheme.onSurfaceVariant,
        text: Color = MaterialTheme.colorScheme.onSurfaceVariant,
        indicator: Color = MaterialTheme.colorScheme.primary,
        selectedIcon: Color = MaterialTheme.colorScheme.tertiary,
        selectedText: Color = MaterialTheme.colorScheme.tertiary
    ) = AppBottomBarColors(
        tonalElevation = tonalElevation,
        shadowElevation = shadowElevation,
        container = container,
        icon = icon,
        text = text,
        indicator = indicator,
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
    val layoutPadding: PaddingValues,
    val containerPadding: PaddingValues,
    val containerHorizontalArrangement: Arrangement.Horizontal,
    val containerVerticalAlignment: Alignment.Vertical,
    val itemPadding: PaddingValues,
    val itemHorizontalAlignment: Alignment.Horizontal,
    val itemVerticalArrangement: Arrangement.Vertical
)

class AppBottomBarColors(
    val tonalElevation: Dp,
    val shadowElevation: Dp,
    val container: Color,
    val icon: Color,
    val text: Color,
    val indicator: Color,
    val selectedIcon: Color,
    val selectedText: Color
)

class AppBottomBarShapes(
    val container: Shape,
    val item: Shape
)