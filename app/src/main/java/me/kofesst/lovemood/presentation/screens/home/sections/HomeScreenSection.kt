package me.kofesst.lovemood.presentation.screens.home.sections

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import me.kofesst.lovemood.core.models.Relationship
import me.kofesst.lovemood.core.text.AppTextHolder

abstract class HomeScreenSection {
    protected open val labelHolder: AppTextHolder?
        @Composable get() = null

    protected open val labelColor: Color
        @Composable get() = MaterialTheme.colorScheme.primary

    protected open val labelStyle: TextStyle
        @Composable get() = MaterialTheme.typography.titleLarge

    protected open val containerModifier: Modifier
        @Composable get() = Modifier

    protected open val containerColor: Color
        @Composable get() = MaterialTheme.colorScheme.surfaceVariant

    protected open val containerContentColor: Color
        @Composable get() = MaterialTheme.colorScheme.onSurfaceVariant

    protected open val containerShape: Shape
        @Composable get() = RoundedCornerShape(20.dp)

    protected open val bodyPadding: PaddingValues
        @Composable get() = PaddingValues(20.dp)

    protected open val bodyColumnArrangement: Arrangement.Vertical
        @Composable get() = Arrangement.spacedBy(10.dp)

    protected open val backgroundImagePainter: Painter?
        @Composable get() = null

    protected open val backgroundImageWidth: Dp
        @Composable get() = 100.dp

    protected open val backgroundImageRotationDegrees: Float
        @Composable get() = -40f

    protected open val backgroundImageTint: Color
        @Composable get() = MaterialTheme.colorScheme.tertiary

    protected open val backgroundImageOffsetRatio: Offset
        @Composable get() = Offset(x = 12f, y = 3f)

    @Composable
    protected open fun ContainerWrapper(modifier: Modifier, content: @Composable () -> Unit) {
        content()
    }

    protected open val headerContent: (@Composable (Modifier, Relationship?) -> Unit)?
        get() = null

    protected abstract val bodyContent: @Composable (Modifier, Relationship?) -> Unit

    @Composable
    operator fun invoke(modifier: Modifier, relationship: Relationship?) {
        ContainerWrapper(modifier) {
            Surface(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .then(containerModifier),
                color = containerColor,
                contentColor = containerContentColor,
                shape = containerShape
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    headerContent?.invoke(Modifier.fillMaxWidth(), relationship)
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.BottomEnd
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bodyPadding),
                            verticalArrangement = bodyColumnArrangement
                        ) {
                            labelHolder?.string()?.let { label ->
                                Text(
                                    text = label,
                                    style = labelStyle,
                                    color = labelColor
                                )
                            }
                            bodyContent(Modifier.fillMaxWidth(), relationship)
                        }
                        backgroundImagePainter?.let { painter ->
                            Image(
                                modifier = Modifier
                                    .width(backgroundImageWidth)
                                    .rotate(backgroundImageRotationDegrees)
                                    .offset(
                                        x = backgroundImageWidth / backgroundImageOffsetRatio.x,
                                        y = backgroundImageWidth / backgroundImageOffsetRatio.y
                                    ),
                                painter = painter,
                                colorFilter = ColorFilter.tint(
                                    color = backgroundImageTint,
                                    blendMode = BlendMode.Modulate
                                ),
                                contentDescription = null
                            )
                        }
                    }
                }
            }
        }
    }
}