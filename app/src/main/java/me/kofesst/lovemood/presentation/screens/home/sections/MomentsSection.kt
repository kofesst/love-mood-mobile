package me.kofesst.lovemood.presentation.screens.home.sections

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import me.kofesst.lovemood.R
import me.kofesst.lovemood.core.models.Relationship
import me.kofesst.lovemood.core.ui.components.cards.BaseCard
import me.kofesst.lovemood.core.ui.components.cards.BaseCardDefaults
import me.kofesst.lovemood.presentation.app.dictionary
import me.kofesst.lovemood.ui.LargeNotImplementedText
import me.kofesst.lovemood.ui.NormalNotImplementedText

@Composable
fun MomentsSection(
    modifier: Modifier = Modifier,
    relationship: Relationship
) {
    BaseCard(
        modifier = modifier,
        colors = BaseCardDefaults.colors(
            backgroundImageTint = Color(0xFFEC99A8)
        ),
        backgroundImagePainter = painterResource(R.drawable.ic_love_camera),
        label = dictionary.screens.home.momentsSectionLabel.string(),
        headerContent = { SectionHeader() }
    ) {
        NormalNotImplementedText()
    }
}

@Composable
private fun SectionHeader(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(MaterialTheme.colorScheme.primaryContainer)
            .fillMaxWidth()
            .height(100.dp),
        contentAlignment = Alignment.Center
    ) {
        CompositionLocalProvider(
            LocalContentColor provides MaterialTheme.colorScheme.onPrimaryContainer
        ) {
            LargeNotImplementedText()
        }
    }
}