package me.kofesst.lovemood.presentation.screens.memory.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddCircleOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import me.kofesst.android.lovemood.navigation.AppScreen
import me.kofesst.lovemood.async.RequiredSnapshotContent
import me.kofesst.lovemood.core.models.PhotoMemory
import me.kofesst.lovemood.core.ui.components.cards.BaseCard
import me.kofesst.lovemood.core.ui.components.image.ByteImage
import me.kofesst.lovemood.core.ui.components.image.ImageViewer
import me.kofesst.lovemood.core.ui.components.image.ImageViewerState
import me.kofesst.lovemood.presentation.Destinations
import me.kofesst.lovemood.presentation.LocalAppState
import me.kofesst.lovemood.presentation.localization

object MemoriesOverviewScreen : AppScreen() {
    @Composable
    override fun ScreenContent(
        modifier: Modifier,
        navBackStackEntry: NavBackStackEntry
    ) {
        val viewModel = hiltViewModel<MemoriesViewModel>(viewModelStoreOwner = navBackStackEntry)
        LaunchedEffect(Unit) {
            viewModel.loadMemories()
        }
        Content(modifier, viewModel)
    }

    @Composable
    private fun Content(
        modifier: Modifier = Modifier,
        viewModel: MemoriesViewModel
    ) {
        val appState = LocalAppState.current
        val memoriesSnapshot by viewModel.memoriesLoadingFlow.collectAsState()
        RequiredSnapshotContent(
            modifier = modifier,
            snapshot = memoriesSnapshot,
            failedContent = {},
            loadedContent = { memories ->
                val imageViewerState = remember { ImageViewerState() }
                ImageViewer(
                    modifier = modifier.fillMaxSize(),
                    state = imageViewerState
                ) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(20.dp),
                        contentPadding = PaddingValues(all = 20.dp)
                    ) {
                        item(key = "add_memory") {
                            AddMemoryCard(
                                modifier = Modifier.fillMaxWidth(),
                                onAddClick = {
                                    appState.navigate(Destinations.MemoryForm)
                                }
                            )
                        }
                        items(items = memories) { memory ->
                            MemoryListItem(
                                modifier = Modifier.fillMaxWidth(),
                                memory = memory,
                                onClick = { imageViewerState.showItem(memory.photoContent) }
                            )
                        }
                    }
                }
            }
        )
    }

    @Composable
    private fun AddMemoryCard(
        modifier: Modifier = Modifier,
        onAddClick: () -> Unit
    ) {
        val localization = localization.screens.memoriesOverview
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
                    text = localization.addNewMemory.build(),
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
        memory: PhotoMemory,
        onClick: () -> Unit
    ) {
        ByteImage(
            modifier = modifier
                .fillMaxWidth()
                .height(500.dp)
                .clip(RoundedCornerShape(20.dp))
                .clickable(onClick = onClick),
            byteContent = memory.photoContent
        )
    }
}