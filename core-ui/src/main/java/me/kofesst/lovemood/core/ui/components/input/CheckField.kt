package me.kofesst.lovemood.core.ui.components.input

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun CheckField(
    modifier: Modifier = Modifier,
    value: Boolean,
    onValueChange: (Boolean) -> Unit,
    defaults: InputFieldContainerDefaults = InputFieldContainerDefaults.defaults(),
    label: String? = null,
    labelStyle: TextStyle = MaterialTheme.typography.bodyLarge
) {
    BaseInputFieldContainer(
        modifier = modifier.clickable { onValueChange(!value) },
        defaults = defaults
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