package me.kofesst.lovemood.core.ui.components.input

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

object TextInputFieldDefaults {
    @Composable
    fun colors(
        container: Color = MaterialTheme.colorScheme.surfaceVariant,
        content: Color = MaterialTheme.colorScheme.onSurfaceVariant,
        label: Color = content,
        placeholder: Color = content.copy(alpha = 0.60f),
        symbolsCounter: Color = content,
        error: Color = MaterialTheme.colorScheme.error
    ) = TextInputFieldColors(
        container = container,
        content = content,
        label = label,
        placeholder = placeholder,
        symbolsCounter = symbolsCounter,
        error = error
    )

    @Composable
    fun typography(
        label: TextStyle = MaterialTheme.typography.titleMedium,
        content: TextStyle = MaterialTheme.typography.bodyLarge,
        symbolsCounter: TextStyle = MaterialTheme.typography.bodyMedium,
        error: TextStyle = MaterialTheme.typography.bodyMedium
    ) = TextInputFieldTypography(
        label = label,
        content = content,
        symbolsCounter = symbolsCounter,
        error = error
    )
}

open class TextInputFieldColors(
    container: Color,
    content: Color,
    val label: Color,
    val placeholder: Color,
    val symbolsCounter: Color,
    val error: Color
) : InputFieldContainerColors(container, content)

open class TextInputFieldTypography(
    val label: TextStyle,
    val content: TextStyle,
    val symbolsCounter: TextStyle,
    val error: TextStyle
)

@Composable
fun TextInputField(
    modifier: Modifier = Modifier,
    colors: TextInputFieldColors = TextInputFieldDefaults.colors(),
    typography: TextInputFieldTypography = TextInputFieldDefaults.typography(),
    contentPadding: PaddingValues = InputFieldContainerDefaults.contentPadding,
    containerShape: Shape = InputFieldContainerDefaults.containerShape,
    value: String,
    onValueChange: (String) -> Unit,
    label: String? = null,
    placeholder: String? = null,
    errorMessage: String? = null,
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
            if (maxSymbols < 1 || newValue.length <= maxSymbols) {
                onValueChange(newValue)
            }
        },
        textStyle = MaterialTheme.typography.bodyLarge.copy(
            color = colors.content
        ),
        cursorBrush = SolidColor(colors.content),
        singleLine = singleLine,
        maxLines = maxLines,
        enabled = enabled,
        readOnly = isReadOnly,
        decorationBox = { innerComposable ->
            TextInputFieldDecoration(
                modifier = Modifier.fillMaxWidth(),
                colors = colors,
                typography = typography,
                contentPadding = contentPadding,
                containerShape = containerShape,
                label = label,
                errorMessage = errorMessage,
                symbolsLength = value.length,
                maxSymbols = maxSymbols.takeIf { it > 0 }
            ) {
                innerComposable()
                if (value.isEmpty() && placeholder != null) {
                    Text(
                        text = placeholder,
                        color = colors.placeholder
                    )
                }
            }
        }
    )
}

@Composable
fun TextInputFieldDecoration(
    modifier: Modifier = Modifier,
    colors: TextInputFieldColors = TextInputFieldDefaults.colors(),
    typography: TextInputFieldTypography = TextInputFieldDefaults.typography(),
    contentPadding: PaddingValues = InputFieldContainerDefaults.contentPadding,
    containerShape: Shape = InputFieldContainerDefaults.containerShape,
    label: String? = null,
    errorMessage: String? = null,
    symbolsLength: Int? = null,
    maxSymbols: Int? = null,
    content: @Composable () -> Unit
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
            CompositionLocalProvider(
                LocalTextStyle provides typography.content
            ) {
                TextInputFieldDecorationBody(
                    modifier = Modifier.fillMaxWidth(),
                    label = label,
                    colors = colors,
                    typography = typography,
                    content = content
                )
            }
            if (errorMessage != null || maxSymbols != null) {
                TextInputFieldDecorationFooter(
                    modifier = Modifier.fillMaxWidth(),
                    colors = colors,
                    typography = typography,
                    errorMessage = errorMessage,
                    symbolsLength = symbolsLength,
                    maxSymbols = maxSymbols
                )
            }
        }
    }
}

@Composable
fun TextInputFieldDecorationBody(
    modifier: Modifier = Modifier,
    colors: TextInputFieldColors = TextInputFieldDefaults.colors(),
    typography: TextInputFieldTypography = TextInputFieldDefaults.typography(),
    label: String? = null,
    content: @Composable () -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        label?.let {
            Text(
                text = it,
                style = typography.label,
                color = colors.label
            )
        }
        Box {
            content()
        }
    }
}

@Composable
fun TextInputFieldDecorationFooter(
    modifier: Modifier = Modifier,
    colors: TextInputFieldColors = TextInputFieldDefaults.colors(),
    typography: TextInputFieldTypography = TextInputFieldDefaults.typography(),
    errorMessage: String? = null,
    symbolsLength: Int? = null,
    maxSymbols: Int? = null
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            modifier = Modifier.weight(1.0f),
            text = errorMessage ?: "",
            style = typography.error,
            color = colors.error
        )
        maxSymbols?.takeIf { it > 0 }?.let {
            Text(
                text = "$symbolsLength / $maxSymbols",
                style = typography.symbolsCounter,
                color = colors.symbolsCounter
            )
        }
    }
}