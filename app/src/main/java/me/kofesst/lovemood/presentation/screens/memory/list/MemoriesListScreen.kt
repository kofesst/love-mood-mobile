package me.kofesst.lovemood.presentation.screens.memory.list

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddCircleOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import me.kofesst.android.lovemood.navigation.AppScreen
import me.kofesst.lovemood.R
import me.kofesst.lovemood.app.AppDestinations
import me.kofesst.lovemood.app.LocalAppState
import me.kofesst.lovemood.app.dictionary
import me.kofesst.lovemood.async.requiredAsyncValueContent
import me.kofesst.lovemood.core.models.PhotoMemory
import me.kofesst.lovemood.core.ui.components.cards.BaseCard
import me.kofesst.lovemood.core.ui.components.scaffold.NavigateUpIconButton
import me.kofesst.lovemood.core.ui.components.scaffold.SmallAppTopBar
import me.kofesst.lovemood.core.ui.transitions.softHorizontalSlide
import me.kofesst.lovemood.core.ui.utils.ByteArrayImage
import me.kofesst.lovemood.presentation.screens.memory.calendar.hasAssociated

object MemoriesListScreen : AppScreen() {
    @Composable
    override fun ScreenContent(
        modifier: Modifier,
        navBackStackEntry: NavBackStackEntry
    ) {
        val appState = LocalAppState.current
        val viewModel = hiltViewModel<MemoriesViewModel>(viewModelStoreOwner = navBackStackEntry)
        LaunchedEffect(Unit) {
            viewModel.loadMemories()
            updateTopBar {
                SmallAppTopBar(
                    modifier = Modifier.fillMaxWidth(),
                    leftContent = {
                        NavigateUpIconButton { appState.navigateUp() }
                    },
                    mainContent = {
                        Text(
                            text = dictionary.screens.memoriesList.screenTitle.string(),
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                )
            }
        }

        Content(modifier, viewModel)
    }

    @Composable
    private fun Content(
        modifier: Modifier = Modifier,
        viewModel: MemoriesViewModel
    ) {
        val appState = LocalAppState.current
        val asyncMemories by viewModel.memoriesState
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            contentPadding = PaddingValues(all = 20.dp)
        ) {
            requiredAsyncValueContent(
                asyncValue = asyncMemories,
                onLoaded = { memories ->
                    item(key = "memories_calendar_card") {
                        MemoriesCalendarCard(
                            modifier = Modifier.fillMaxWidth(),
                            hasAssociatedMemories = memories.hasAssociated(),
                            onShowClick = {
                                appState.navigate(AppDestinations.Memories.Calendar)
                            }
                        )
                    }
                    item(key = "add_memory") {
                        AddMemoryCard(
                            modifier = Modifier.fillMaxWidth(),
                            onAddClick = {
                                appState.navigate(AppDestinations.Forms.Memory)
                            }
                        )
                    }
                    items(
                        count = memories.size,
                        key = { "memory_item_${it}" }
                    ) {
                        MemoryListItem(
                            modifier = Modifier.fillMaxWidth(),
                            memory = memories[it]
                        )
                    }
                }
            )
        }
    }

    @Composable
    private fun MemoriesCalendarCard(
        modifier: Modifier = Modifier,
        hasAssociatedMemories: Boolean,
        onShowClick: () -> Unit
    ) {
        val dictionary = dictionary.screens.memoriesList
        AnimatedContent(
            label = "memories_calendar_card",
            targetState = hasAssociatedMemories,
            transitionSpec = { softHorizontalSlide }
        ) { isCardVisible ->
            if (isCardVisible) {
                BaseCard(
                    modifier = modifier,
                    backgroundImagePainter = painterResource(R.drawable.ic_couple_love_calendar),
                    label = dictionary.memoriesCalendarCardLabel.string()
                ) {
                    Text(text = dictionary.memoriesCalendarCardText.string())
                    TextButton(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = onShowClick
                    ) {
                        Text(text = dictionary.openMemoriesCalendarAction.string())
                    }
                }
            }
        }
    }

    @Composable
    private fun AddMemoryCard(
        modifier: Modifier = Modifier,
        onAddClick: () -> Unit
    ) {
        val dictionary = dictionary.screens.memoriesList
        BaseCard(
            modifier = modifier,
            backgroundImagePainter = null,
            onClick = onAddClick
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Rounded.AddCircleOutline,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = dictionary.addNewMemoryAction.string(),
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center
                )
            }
        }
    }

    @Composable
    private fun MemoryListItem(
        modifier: Modifier = Modifier,
        memory: PhotoMemory
    ) {
        ByteArrayImage(
            modifier = modifier
                .fillMaxWidth()
                .height(500.dp)
                .clip(RoundedCornerShape(20.dp)),
            content = memory.photoContent
        )
    }
}