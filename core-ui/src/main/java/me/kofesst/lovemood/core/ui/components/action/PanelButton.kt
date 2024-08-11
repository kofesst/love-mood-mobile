package me.kofesst.lovemood.core.ui.components.action

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class PanelButtonDefaults(
    val textStyle: TextStyle,
    val textColor: Color,
    val clip: Clip,
    val clipSize: Dp,
    val paddingValues: PaddingValues
) {
    companion object {
        @Composable
        fun defaults(
            textStyle: TextStyle = MaterialTheme.typography.bodyLarge,
            textColor: Color = MaterialTheme.colorScheme.primary,
            clip: Clip = Clip.None,
            clipSize: Dp = 20.dp,
            paddingValues: PaddingValues = PaddingValues(horizontal = 20.dp, vertical = 10.dp)
        ) = PanelButtonDefaults(textStyle, textColor, clip, clipSize, paddingValues)
    }

    open class Clip(private val flag: Int) {
        object TopStart : Clip(0x0001)
        object TopEnd : Clip(0x0010)
        object BottomStart : Clip(0x0100)
        object BottomEnd : Clip(0x1000)

        object All : Clip(0x1111)
        object Top : Clip(0x0011)
        object Bottom : Clip(0x1100)
        object Start : Clip(0x0101)
        object End : Clip(0x1010)

        object None : Clip(0)

        operator fun plus(other: Clip) = Clip(flag.or(other.flag))

        fun containsFlag(other: Clip) = flag.and(other.flag) != 0

        fun asShape(clipSize: Dp) = RoundedCornerShape(
            topStart = if (containsFlag(TopStart)) clipSize else 0.dp,
            topEnd = if (containsFlag(TopEnd)) clipSize else 0.dp,
            bottomStart = if (containsFlag(BottomStart)) clipSize else 0.dp,
            bottomEnd = if (containsFlag(BottomEnd)) clipSize else 0.dp,
        )
    }
}

@Composable
fun PanelButton(
    modifier: Modifier = Modifier,
    action: String,
    onClick: () -> Unit,
    defaults: PanelButtonDefaults = PanelButtonDefaults.defaults(),
) {
    Box(
        modifier = modifier
            .clip(defaults.clip.asShape(defaults.clipSize))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = action,
            style = defaults.textStyle.copy(
                color = defaults.textColor,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier.padding(defaults.paddingValues)
        )
    }
}

@Composable
fun PanelButton(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    action: String,
    onClick: () -> Unit,
    defaults: PanelButtonDefaults = PanelButtonDefaults.defaults(),
) {
    LazyRow(
        modifier = modifier
            .clip(defaults.clip.asShape(defaults.clipSize))
            .clickable(onClick = onClick),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        contentPadding = defaults.paddingValues
    ) {
        item(key = "icon") {
            Icon(
                modifier = Modifier.size(24.dp),
                imageVector = icon,
                contentDescription = null,
                tint = defaults.textColor
            )
        }
        item(key = "action_text") {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = action,
                style = defaults.textStyle.copy(
                    color = defaults.textColor,
                    textAlign = TextAlign.Start
                )
            )
        }
    }
}