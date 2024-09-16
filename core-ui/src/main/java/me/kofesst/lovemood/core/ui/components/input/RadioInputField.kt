package me.kofesst.lovemood.core.ui.components.input

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.RadioButtonChecked
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

object RadioInputFieldDefaults {
    val indicatorImageVector: ImageVector
        @Composable get() = Icons.Rounded.RadioButtonChecked

    val indicatorSize: Dp
        @Composable get() = 24.dp
}

@Composable
fun <T : Any> RadioInputField(
    modifier: Modifier = Modifier,
    colors: InputFieldContainerColors = InputFieldContainerDefaults.colors(),
    contentPadding: PaddingValues = InputFieldContainerDefaults.contentPadding,
    containerShape: Shape = InputFieldContainerDefaults.containerShape,
    label: String,
    selected: T,
    items: List<T>,
    itemContent: @Composable RowScope.(item: T, selected: Boolean) -> Unit
) {
    InputFieldContainer(
        modifier = modifier,
        colors = colors,
        contentPadding = contentPadding,
        containerShape = containerShape
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = label,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min),
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                items.forEach { item ->
                    itemContent(item, item == selected)
                }
            }
        }
    }
}

@Composable
fun RowScope.RadioInputFieldItem(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    indicatorIcon: ImageVector = RadioInputFieldDefaults.indicatorImageVector,
    indicatorSize: Dp = RadioInputFieldDefaults.indicatorSize,
    itemContainer: @Composable (Modifier, inner: @Composable () -> Unit) -> Unit,
    itemContent: @Composable RowScope.() -> Unit
) {
    itemContainer(
        modifier
            .weight(1.0f)
            .fillMaxHeight()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AnimatedContent(
                targetState = isSelected,
                label = "selected_indicator"
            ) { isSelected ->
                if (isSelected) {
                    Icon(
                        imageVector = indicatorIcon,
                        contentDescription = null,
                        modifier = Modifier.size(indicatorSize)
                    )
                }
            }
            itemContent()
        }
    }
}