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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import me.kofesst.lovemood.R
import me.kofesst.lovemood.core.models.Relationship
import me.kofesst.lovemood.core.text.AppTextHolder
import me.kofesst.lovemood.presentation.app.LocalDictionary
import me.kofesst.lovemood.ui.LargeNotImplementedText
import me.kofesst.lovemood.ui.NormalNotImplementedText

object MomentsSection : HomeScreenSection() {
    override val labelHolder: AppTextHolder
        @Composable get() = LocalDictionary.current.screens.home.momentsSectionLabel

    override val backgroundImagePainter: Painter
        @Composable get() = painterResource(R.drawable.ic_love_camera)

    override val backgroundImageTint: Color
        @Composable get() = Color(0xFFEC99A8)

    override val headerContent: @Composable (Modifier, Relationship?) -> Unit
        get() = { _, _ ->
            Box(
                modifier = Modifier
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

    override val bodyContent: @Composable (Modifier, Relationship?) -> Unit
        get() = { _, _ ->
            NormalNotImplementedText()
        }
}