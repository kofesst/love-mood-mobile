package me.kofesst.lovemood.presentation.screens.home.sections

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import me.kofesst.lovemood.R
import me.kofesst.lovemood.app.dictionary
import me.kofesst.lovemood.core.models.PhotoMemory
import me.kofesst.lovemood.core.ui.components.cards.BaseCard

@Composable
fun MemoriesSection(
    modifier: Modifier = Modifier,
    memories: List<PhotoMemory>,
    onAddMemoryClick: () -> Unit,
    onViewAllClick: () -> Unit
) {
    BaseCard(
        modifier = modifier,
        backgroundImagePainter = painterResource(R.drawable.ic_love_camera),
        label = dictionary.screens.home.memoriesSectionLabel.string()
    ) {
        SectionContent(
            isMemoriesEmpty = memories.isEmpty(),
            onAddClick = onAddMemoryClick,
            onViewAllClick = onViewAllClick
        )
    }
}

@Composable
private fun SectionContent(
    isMemoriesEmpty: Boolean,
    onAddClick: () -> Unit,
    onViewAllClick: () -> Unit
) {
    val dictionary = dictionary.screens.home
    if (isMemoriesEmpty) {
        Text(text = dictionary.doNotHaveMemories.string())
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onAddClick
        ) {
            Text(text = dictionary.addFirstMemoryAction.string())
        }
    } else {
        Text(text = dictionary.memoriesContentText.string())
        TextButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = onViewAllClick
        ) {
            Text(text = dictionary.viewAllMemoriesAction.string())
        }
    }
}