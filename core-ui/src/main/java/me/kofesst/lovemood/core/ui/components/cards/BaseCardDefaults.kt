package me.kofesst.lovemood.core.ui.components.cards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp

object BaseCardDefaults {
    val containerShape: Shape
        @Composable get() = MaterialTheme.shapes.large

    @Composable
    fun colors(
        container: Color = MaterialTheme.colorScheme.surfaceVariant,
        labelContent: Color = MaterialTheme.colorScheme.primary,
        mainContent: Color = MaterialTheme.colorScheme.onSurfaceVariant,
        backgroundImageTint: Color = MaterialTheme.colorScheme.tertiary
    ) = BaseCardColors(
        container = container,
        labelContent = labelContent,
        mainContent = mainContent,
        backgroundImageTint = backgroundImageTint
    )

    @Composable
    fun layout(
        containerPadding: PaddingValues = PaddingValues(all = 0.dp),
        containerHorizontalAlignment: Alignment.Horizontal = Alignment.Start,
        containerVerticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(
            space = 20.dp,
            alignment = Alignment.Top
        ),
        bodyPadding: PaddingValues = PaddingValues(all = 16.dp),
        bodyHorizontalAlignment: Alignment.Horizontal = Alignment.Start,
        bodyVerticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(
            space = 10.dp,
            alignment = Alignment.Top
        )
    ) = BaseCardLayout(
        containerPadding = containerPadding,
        containerHorizontalAlignment = containerHorizontalAlignment,
        containerVerticalArrangement = containerVerticalArrangement,
        bodyPadding = bodyPadding,
        bodyHorizontalAlignment = bodyHorizontalAlignment,
        bodyVerticalArrangement = bodyVerticalArrangement
    )

    @Composable
    fun dimensions(
        backgroundImageWidth: Dp = 100.dp,
        backgroundImageRotationDegrees: Float = -40f,
        backgroundImageOffsetRatio: Offset = Offset(x = 12f, y = 3f)
    ) = BaseCardDimensions(
        backgroundImageWidth = backgroundImageWidth,
        backgroundImageRotationDegrees = backgroundImageRotationDegrees,
        backgroundImageOffsetRatio = backgroundImageOffsetRatio
    )

    @Composable
    fun styles(
        label: TextStyle = MaterialTheme.typography.titleLarge,
        mainContent: TextStyle = MaterialTheme.typography.bodyLarge
    ) = BaseCardStyles(
        label = label,
        mainContent = mainContent
    )
}

data class BaseCardColors(
    val container: Color,
    val labelContent: Color,
    val mainContent: Color,
    val backgroundImageTint: Color
)

data class BaseCardLayout(
    val containerPadding: PaddingValues,
    val containerHorizontalAlignment: Alignment.Horizontal,
    val containerVerticalArrangement: Arrangement.Vertical,
    val bodyPadding: PaddingValues,
    val bodyHorizontalAlignment: Alignment.Horizontal,
    val bodyVerticalArrangement: Arrangement.Vertical
)

data class BaseCardDimensions(
    val backgroundImageWidth: Dp,
    val backgroundImageRotationDegrees: Float,
    val backgroundImageOffsetRatio: Offset
) {
    val backgroundImageOffset: DpOffset = DpOffset(
        x = backgroundImageWidth / backgroundImageOffsetRatio.x,
        y = backgroundImageWidth / backgroundImageOffsetRatio.y
    )
}

data class BaseCardStyles(
    val label: TextStyle,
    val mainContent: TextStyle
)