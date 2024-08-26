package me.kofesst.lovemood.core.ui.components.input

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

object CheckFieldDefaults {
    val labelStyle: TextStyle
        @Composable get() = MaterialTheme.typography.bodyLarge
}

@Composable
fun CheckField(
    modifier: Modifier = Modifier,
    value: Boolean,
    onValueChange: (Boolean) -> Unit,
    label: String? = null,
    labelStyle: TextStyle = CheckFieldDefaults.labelStyle,
    colors: InputFieldContainerColors = InputFieldContainerDefaults.colors(),
    contentPadding: PaddingValues = InputFieldContainerDefaults.contentPadding,
    containerShape: Shape = InputFieldContainerDefaults.containerShape
) {
    InputFieldContainer(
        modifier = modifier.pointerInput(Unit) {
            detectTapGestures {
                onValueChange(!value)
            }
        },
        colors = colors,
        contentPadding = contentPadding,
        containerShape = containerShape
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = value,
                onCheckedChange = null
            )
            label?.let {
                Text(
                    text = it,
                    style = labelStyle
                )
            }
        }
    }
}