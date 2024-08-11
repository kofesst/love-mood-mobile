package me.kofesst.lovemood.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import me.kofesst.lovemood.presentation.app.LocalDictionary

@Composable
fun LargeNotImplementedText(
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start
) {
    NotImplementedText(
        modifier = modifier,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
        fontStyle = FontStyle.Italic,
        textAlign = textAlign
    )
}

@Composable
fun NormalNotImplementedText(
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start
) {
    NotImplementedText(
        modifier = modifier,
        style = MaterialTheme.typography.bodyLarge,
        fontWeight = FontWeight.Normal,
        fontStyle = FontStyle.Italic,
        textAlign = textAlign
    )
}

@Composable
fun SmallNotImplementedText(
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start
) {
    NotImplementedText(
        modifier = modifier,
        style = MaterialTheme.typography.labelLarge,
        fontWeight = FontWeight.Normal,
        fontStyle = FontStyle.Italic,
        textAlign = textAlign
    )
}

@Composable
fun NotImplementedText(
    modifier: Modifier = Modifier,
    style: TextStyle,
    fontWeight: FontWeight,
    fontStyle: FontStyle,
    textAlign: TextAlign
) {
    Text(
        modifier = modifier,
        text = LocalDictionary.current.notImplementedYet.string(),
        style = style,
        fontWeight = fontWeight,
        fontStyle = fontStyle,
        textAlign = textAlign
    )
}