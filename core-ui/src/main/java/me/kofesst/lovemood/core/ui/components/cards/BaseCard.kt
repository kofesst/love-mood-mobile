package me.kofesst.lovemood.core.ui.components.cards

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter

@Composable
fun BaseCard(
    modifier: Modifier = Modifier,
    containerShape: Shape = BaseCardDefaults.containerShape,
    colors: BaseCardColors = BaseCardDefaults.colors(),
    layout: BaseCardLayout = BaseCardDefaults.layout(),
    dimensions: BaseCardDimensions = BaseCardDefaults.dimensions(),
    styles: BaseCardStyles = BaseCardDefaults.styles(),
    label: String? = null,
    headerContent: (@Composable () -> Unit)? = null,
    backgroundImagePainter: Painter?,
    bodyContent: @Composable ColumnScope.() -> Unit
) {
    Surface(
        modifier = modifier,
        color = colors.container,
        contentColor = colors.mainContent,
        shape = containerShape
    ) {
        CardContent(
            modifier = Modifier.fillMaxWidth(),
            colors = colors,
            layout = layout,
            dimensions = dimensions,
            styles = styles,
            label = label,
            headerContent = headerContent,
            backgroundImagePainter = backgroundImagePainter,
            bodyContent = bodyContent
        )
    }
}

@Composable
private fun CardContent(
    modifier: Modifier = Modifier,
    colors: BaseCardColors,
    layout: BaseCardLayout,
    dimensions: BaseCardDimensions,
    styles: BaseCardStyles,
    label: String?,
    headerContent: (@Composable () -> Unit)?,
    backgroundImagePainter: Painter?,
    bodyContent: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier.padding(layout.containerPadding),
        horizontalAlignment = layout.containerHorizontalAlignment,
        verticalArrangement = layout.containerVerticalArrangement
    ) {
        headerContent?.invoke()
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.BottomEnd
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(layout.bodyPadding),
                horizontalAlignment = layout.bodyHorizontalAlignment,
                verticalArrangement = layout.bodyVerticalArrangement
            ) {
                label?.let {
                    Text(
                        text = it,
                        style = styles.label,
                        color = colors.labelContent
                    )
                }
                bodyContent(this)
            }
            CardImage(
                colors = colors,
                dimensions = dimensions,
                backgroundImagePainter = backgroundImagePainter
            )
        }
    }
}

@Composable
private fun CardImage(
    modifier: Modifier = Modifier,
    colors: BaseCardColors,
    dimensions: BaseCardDimensions,
    backgroundImagePainter: Painter?
) {
    backgroundImagePainter?.let { painter ->
        Image(
            modifier = modifier
                .width(dimensions.backgroundImageWidth)
                .rotate(dimensions.backgroundImageRotationDegrees)
                .offset(
                    x = dimensions.backgroundImageOffset.x,
                    y = dimensions.backgroundImageOffset.y
                ),
            painter = painter,
            colorFilter = ColorFilter.tint(
                color = colors.backgroundImageTint,
                blendMode = BlendMode.Modulate
            ),
            contentDescription = null
        )
    }
}