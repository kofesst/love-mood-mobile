package me.kofesst.lovemood.core.ui.components.input

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

data class InputFieldContainerDefaults(
    val shape: Shape,
    val contentPadding: PaddingValues,
    val color: Color,
    val contentColor: Color
) {
    companion object {
        @Composable
        fun defaults(
            shape: Shape = RoundedCornerShape(size = 20.dp),
            contentPadding: PaddingValues = PaddingValues(all = 20.dp),
            color: Color = MaterialTheme.colorScheme.surfaceVariant,
            contentColor: Color = MaterialTheme.colorScheme.onSurfaceVariant
        ) = InputFieldContainerDefaults(
            shape = shape,
            contentPadding = contentPadding,
            color = color,
            contentColor = contentColor
        )
    }
}

@Composable
fun TextInputFieldContainer(
    modifier: Modifier = Modifier,
    defaults: InputFieldContainerDefaults = InputFieldContainerDefaults.defaults(),
    label: String?,
    error: String?,
    currentSymbols: Int,
    maxSymbols: Int?,
    content: @Composable () -> Unit
) {
    BaseInputFieldContainer(
        modifier = modifier,
        defaults = defaults
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            TextInputFieldContainerBody(
                modifier = Modifier.fillMaxWidth(),
                label = label,
                content = {
                    content()
                }
            )
            if (error != null || maxSymbols != null) {
                TextInputFieldContainerFooter(
                    modifier = Modifier.fillMaxWidth(),
                    error = error,
                    currentSymbols = currentSymbols,
                    maxSymbols = maxSymbols
                )
            }
        }
    }
}

@Composable
fun BaseInputFieldContainer(
    modifier: Modifier = Modifier,
    defaults: InputFieldContainerDefaults = InputFieldContainerDefaults.defaults(),
    content: @Composable () -> Unit
) {
    Surface(
        modifier = modifier.animateContentSize(),
        shape = defaults.shape,
        color = defaults.color,
        contentColor = defaults.contentColor
    ) {
        Box(Modifier.padding(defaults.contentPadding)) {
            content()
        }
    }
}

@Composable
fun TextInputFieldContainerBody(
    modifier: Modifier = Modifier,
    label: String?,
    content: @Composable () -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        label?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Normal
            )
        }
        Box {
            content()
        }
    }
}

@Composable
fun TextInputFieldContainerFooter(
    modifier: Modifier = Modifier,
    error: String?,
    currentSymbols: Int,
    maxSymbols: Int?
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            modifier = Modifier.weight(1.0f),
            text = error ?: "",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.error
        )
        maxSymbols?.takeIf { it > 0 }?.let {
            Text(
                text = "$currentSymbols / $maxSymbols",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}