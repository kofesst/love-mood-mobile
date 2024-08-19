package me.kofesst.lovemood.presentation.screens.home.sections

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import me.kofesst.lovemood.R
import me.kofesst.lovemood.app.dictionary
import me.kofesst.lovemood.core.models.Relationship
import me.kofesst.lovemood.core.ui.components.cards.BaseCard
import me.kofesst.lovemood.core.ui.components.cards.BaseCardDefaults
import me.kofesst.lovemood.ui.NormalNotImplementedText

@Composable
fun EventsSection(
    modifier: Modifier = Modifier,
    relationship: Relationship
) {
    BaseCard(
        modifier = modifier,
        backgroundImagePainter = painterResource(R.drawable.ic_love_calendar),
        colors = BaseCardDefaults.colors(
            backgroundImageTint = Color(0xFFCEA0E8)
        ),
        label = dictionary.screens.home.eventsSectionLabel.string()
    ) {
        NormalNotImplementedText()
    }
}