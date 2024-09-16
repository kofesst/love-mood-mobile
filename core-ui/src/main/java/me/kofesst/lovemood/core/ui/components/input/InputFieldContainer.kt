package me.kofesst.lovemood.core.ui.components.input

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

object InputFieldContainerDefaults {
    val contentPadding: PaddingValues
        @Composable get() = PaddingValues(all = 20.dp)

    val containerShape: Shape
        @Composable get() = MaterialTheme.shapes.medium

    @Composable
    fun colors(
        container: Color = MaterialTheme.colorScheme.surfaceVariant,
        content: Color = MaterialTheme.colorScheme.onSurfaceVariant
    ) = InputFieldContainerColors(
        container = container,
        content = content
    )
}

open class InputFieldContainerColors(
    val container: Color,
    val content: Color
)

@Composable
fun InputFieldContainer(
    modifier: Modifier = Modifier,
    colors: InputFieldContainerColors = InputFieldContainerDefaults.colors(),
    contentPadding: PaddingValues = InputFieldContainerDefaults.contentPadding,
    containerShape: Shape = InputFieldContainerDefaults.containerShape,
    content: @Composable () -> Unit
) {
    Surface(
        modifier = modifier.animateContentSize(),
        color = colors.container,
        contentColor = colors.content,
        shape = containerShape
    ) {
        Box(
            modifier = Modifier.padding(contentPadding)
        ) {
            content()
        }
    }
}