package me.kofesst.lovemood.core.ui.components.input

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.RadioButtonChecked
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import me.kofesst.lovemood.core.models.Gender

@Preview
@Composable
private fun RadioInputFieldPreview() {
    RadioInputField(
        modifier = Modifier.width(400.dp),
        label = "Choose your gender",
        selected = Gender.Male,
        items = Gender.entries.toList(),
    ) { gender, selected ->
        RadioInputFieldItem(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1.0f),
            isSelected = selected,
            itemContainer = { modifier, inner ->
                Surface(
                    modifier = modifier,
                    color = if (gender == Gender.Male) Color.Blue
                    else Color.Magenta,
                    contentColor = Color.White,
                    shape = RoundedCornerShape(size = 20.dp)
                ) {
                    inner()
                }
            },
            itemContent = {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = gender.name,
                    textDecoration = if (selected) TextDecoration.Underline
                    else TextDecoration.None,
                    textAlign = TextAlign.Center
                )
            }
        )
    }
}

@Composable
fun <T : Any> RadioInputField(
    modifier: Modifier = Modifier,
    defaults: InputFieldContainerDefaults = InputFieldContainerDefaults.defaults(),
    label: String,
    selected: T,
    items: List<T>,
    itemContent: @Composable RowScope.(item: T, selected: Boolean) -> Unit
) {
    BaseInputFieldContainer(
        modifier = modifier,
        defaults = defaults
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
    selectedIndicatorIcon: ImageVector = Icons.Rounded.RadioButtonChecked,
    selectedIndicatorIconSize: Dp = 24.dp,
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
                        imageVector = selectedIndicatorIcon,
                        contentDescription = null,
                        modifier = Modifier.size(selectedIndicatorIconSize)
                    )
                }
            }
            itemContent()
        }
    }
}