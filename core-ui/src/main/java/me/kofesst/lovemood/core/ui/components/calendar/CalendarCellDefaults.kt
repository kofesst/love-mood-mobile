package me.kofesst.lovemood.core.ui.components.calendar

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class CalendarCellDefaults(
    val containerColor: Color,
    val contentColor: Color,
    val containerShape: Shape,
    val containerSize: Dp,
    val textStyle: TextStyle
) {
    companion object {
        @Composable
        fun defaults(
            containerColor: Color = MaterialTheme.colorScheme.surfaceVariant,
            contentColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
            containerShape: Shape = CircleShape,
            containerSize: Dp = 48.dp,
            textStyle: TextStyle = MaterialTheme.typography.bodyLarge
        ) = CalendarCellDefaults(
            containerColor = containerColor,
            contentColor = contentColor,
            containerShape = containerShape,
            containerSize = containerSize,
            textStyle = textStyle
        )

        @Composable
        internal fun stubDefaults(
            containerColor: Color = Color.Transparent,
            contentColor: Color = Color.Transparent,
            containerShape: Shape = CircleShape,
            containerSize: Dp = 48.dp,
            textStyle: TextStyle = TextStyle.Default
        ) = CalendarCellDefaults(
            containerColor = containerColor,
            contentColor = contentColor,
            containerShape = containerShape,
            containerSize = containerSize,
            textStyle = textStyle
        )
    }
}