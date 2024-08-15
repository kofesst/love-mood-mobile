package me.kofesst.lovemood.presentation.screens.app

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun AppScreenCard(
    modifier: Modifier = Modifier,
    title: String? = null,
    subtitle: String? = null,
    header: @Composable ColumnScope.() -> Unit = {},
    footer: @Composable ColumnScope.() -> Unit = {},
    text: String
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surfaceVariant,
        contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
        shape = RoundedCornerShape(20.dp)
    ) {
        CardContent(
            title = title,
            subtitle = subtitle,
            header = header,
            footer = footer,
            text = text
        )
    }
}

@Composable
fun AppScreenCard(
    modifier: Modifier = Modifier,
    title: String? = null,
    subtitle: String? = null,
    header: @Composable ColumnScope.() -> Unit = {},
    footer: @Composable ColumnScope.() -> Unit = {},
    text: String,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surfaceVariant,
        contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
        shape = RoundedCornerShape(20.dp),
        onClick = onClick
    ) {
        CardContent(
            title = title,
            subtitle = subtitle,
            header = header,
            footer = footer,
            text = text
        )
    }
}

@Composable
private fun CardContent(
    modifier: Modifier = Modifier,
    title: String? = null,
    subtitle: String? = null,
    header: @Composable ColumnScope.() -> Unit = {},
    footer: @Composable ColumnScope.() -> Unit = {},
    text: String
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(space = 10.dp)
    ) {
        header()
        title?.let {
            CardText(
                modifier = Modifier.fillMaxWidth(),
                isMain = true,
                text = it,
                style = MaterialTheme.typography.titleLarge
            )
        }
        subtitle?.let {
            CardText(
                modifier = Modifier.fillMaxWidth(),
                isMain = true,
                text = it,
                style = MaterialTheme.typography.titleMedium
            )
        }
        CardText(
            modifier = Modifier.fillMaxWidth(),
            isMain = false,
            text = text,
            style = MaterialTheme.typography.bodyLarge
        )
        footer()
    }
}

@Composable
private fun CardText(
    modifier: Modifier = Modifier,
    isMain: Boolean,
    text: String,
    style: TextStyle
) {
    Text(
        modifier = modifier,
        text = text,
        style = style,
        fontWeight = if (isMain) FontWeight.Bold else FontWeight.Normal,
        color = if (isMain) MaterialTheme.colorScheme.primary else LocalContentColor.current
    )
}