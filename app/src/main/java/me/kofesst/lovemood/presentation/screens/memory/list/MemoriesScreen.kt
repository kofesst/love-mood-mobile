package me.kofesst.lovemood.presentation.screens.memory.list

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.AddCircleOutline
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import me.kofesst.lovemood.R
import me.kofesst.lovemood.core.models.PhotoMemory
import me.kofesst.lovemood.core.ui.components.cards.BaseCard
import me.kofesst.lovemood.core.ui.components.cards.BaseCardDefaults
import me.kofesst.lovemood.core.ui.components.scaffold.SmallAppTopBar
import me.kofesst.lovemood.core.ui.utils.ByteArrayImage
import me.kofesst.lovemood.presentation.app.LocalAppState
import me.kofesst.lovemood.presentation.navigation.AppNavigation
import me.kofesst.lovemood.presentation.screens.memory.calendar.hasAssociated
import me.kofesst.lovemood.ui.async.requiredAsyncValueContent

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MemoriesScreen(
    modifier: Modifier = Modifier,
    navBackStackEntry: NavBackStackEntry
) {
    val viewModel = hiltViewModel<MemoriesViewModel>(
        viewModelStoreOwner = navBackStackEntry
    )
    LaunchedEffect(Unit) {
        viewModel.loadMemories()
    }

    val appState = LocalAppState.current
    val asyncMemories by viewModel.memoriesState
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        contentPadding = PaddingValues(all = 20.dp)
    ) {
        stickyHeader(key = "screen_header") {
            ScreenHeader(onBackClick = appState::navigateUp)
        }
        requiredAsyncValueContent(
            asyncValue = asyncMemories,
            onLoaded = { memories ->
                item(key = "memories_calendar_card") {
                    MemoriesCalendarCard(
                        modifier = Modifier.fillMaxWidth(),
                        hasAssociatedMemories = memories.hasAssociated(),
                        onShowClick = {
                            appState.navigate(AppNavigation.MemoriesCalendarScreen)
                        }
                    )
                }
                item(key = "add_memory") {
                    AddMemoryCard(
                        modifier = Modifier.fillMaxWidth(),
                        onAddClick = {
                            appState.navigate(AppNavigation.MemoryForm)
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
private fun ScreenHeader(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit
) {
    SmallAppTopBar(
        modifier = modifier,
        leftContent = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                    contentDescription = null
                )
            }
        },
        mainContent = {
            Text(
                text = "Ваши воспоминания",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }
    )
}

@Composable
private fun MemoriesCalendarCard(
    modifier: Modifier = Modifier,
    hasAssociatedMemories: Boolean,
    onShowClick: () -> Unit
) {
    AnimatedContent(
        label = "memories_calendar_card",
        targetState = hasAssociatedMemories,
        transitionSpec = {
            // todo move into constant
            (fadeIn() + scaleIn()).togetherWith(fadeOut() + scaleOut())
        }
    ) { isCardVisible ->
        if (isCardVisible) {
            BaseCard(
                modifier = modifier,
                backgroundImagePainter = painterResource(R.drawable.ic_couple_love_calendar),
                colors = BaseCardDefaults.colors(
                    backgroundImageTint = Color(0xFFFF94E8)
                ),
                label = "Календарь воспоминаний"
            ) {
                Text(text = "Посмотрите каждое ваше воспоминание в виде календаря и постарайтесь заполнить каждый день")
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onShowClick
                ) {
                    Text(text = "Открыть календарь")
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
                text = "Добавить момент",
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