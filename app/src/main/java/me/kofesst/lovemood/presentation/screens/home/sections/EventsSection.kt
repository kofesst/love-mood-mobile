package me.kofesst.lovemood.presentation.screens.home.sections

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import me.kofesst.lovemood.R
import me.kofesst.lovemood.core.models.Relationship
import me.kofesst.lovemood.core.text.AppTextHolder
import me.kofesst.lovemood.presentation.app.LocalDictionary
import me.kofesst.lovemood.ui.NormalNotImplementedText

object EventsSection : HomeScreenSection() {
    override val labelHolder: AppTextHolder
        @Composable get() = LocalDictionary.current.screens.home.eventsSectionLabel

    override val backgroundImagePainter: Painter
        @Composable get() = painterResource(R.drawable.ic_love_calendar)

    override val backgroundImageTint: Color
        @Composable get() = Color(0xFFCEA0E8)

    override val bodyContent: @Composable (Modifier, Relationship?) -> Unit
        get() = { _, _ ->
            NormalNotImplementedText()
        }
}