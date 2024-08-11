package me.kofesst.lovemood.core.ui.components.input

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
private fun TextInputFieldPreview() {
    TextInputField(
        modifier = Modifier.width(250.dp),
        value = "",
        onValueChange = {},
        label = "Input field",
        placeholder = "Placeholder",
        error = "Error text",
        maxSymbols = 25
    )
}

@Composable
fun TextInputField(
    modifier: Modifier = Modifier,
    defaults: InputFieldContainerDefaults = InputFieldContainerDefaults.defaults(),
    value: String,
    onValueChange: (String) -> Unit,
    label: String? = null,
    placeholder: String? = null,
    error: String? = null,
    maxSymbols: Int = -1,
    singleLine: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    enabled: Boolean = true,
    isReadOnly: Boolean = false
) {
    BasicTextField(
        modifier = modifier,
        value = value,
        onValueChange = { newValue ->
            Log.d("LoveMood", "Value changed: $newValue")
//            if (maxSymbols < 1 || newValue.length <= maxSymbols) {
            onValueChange(newValue)
//            }
        },
        textStyle = MaterialTheme.typography.bodyLarge.copy(
            color = defaults.contentColor
        ),
        cursorBrush = SolidColor(defaults.contentColor),
        singleLine = singleLine,
        maxLines = maxLines,
        enabled = enabled,
        readOnly = isReadOnly,
        decorationBox = { innerComposable ->
            TextInputFieldContainer(
                modifier = Modifier.fillMaxWidth(),
                defaults = defaults,
                label = label,
                error = error,
                currentSymbols = value.length,
                maxSymbols = maxSymbols.takeIf { it > 0 }
            ) {
                innerComposable()
                if (value.isEmpty() && placeholder != null) {
                    Text(
                        text = placeholder,
                        style = MaterialTheme.typography.bodyLarge,
                        color = LocalContentColor.current.copy(alpha = 0.65f)
                    )
                }
            }
        }
    )
}