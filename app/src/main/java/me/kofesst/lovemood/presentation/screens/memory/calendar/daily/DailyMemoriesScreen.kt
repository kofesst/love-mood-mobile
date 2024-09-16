package me.kofesst.lovemood.presentation.screens.memory.calendar.daily

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBackIos
import androidx.compose.material.icons.automirrored.rounded.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import me.kofesst.android.lovemood.navigation.AppScreen
import me.kofesst.lovemood.app.AppDestinations
import me.kofesst.lovemood.app.LocalAppState
import me.kofesst.lovemood.app.LocalDateTimePattern
import me.kofesst.lovemood.async.RequiredAsyncValueContent
import me.kofesst.lovemood.core.ui.components.image.ByteImage
import me.kofesst.lovemood.core.ui.components.image.ImageViewer
import me.kofesst.lovemood.core.ui.components.image.ImageViewerState
import me.kofesst.lovemood.core.ui.components.scaffold.SmallAppTopBar
import me.kofesst.lovemood.core.ui.utils.alsoNavBar
import me.kofesst.lovemood.presentation.screens.memory.list.MemoriesViewModel
import java.time.LocalDate

object DailyMemoriesScreen : AppScreen() {
    @Composable
    override fun ScreenContent(
        modifier: Modifier,
        navBackStackEntry: NavBackStackEntry
    ) {
        val initialDate = rememberArgument(
            argument = AppDestinations.Memories.Daily.dateArgument,
            navBackStackEntry = navBackStackEntry
        )
        val dateTimePattern = LocalDateTimePattern.current
        val appState = LocalAppState.current
        var date by remember { mutableStateOf(initialDate) }
        LaunchedEffect(date) {
            updateTopBar {
                SmallAppTopBar(
                    modifier = Modifier.fillMaxWidth(),
                    leftContent = {
                        IconButton(onClick = { date = date.minusDays(1) }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Rounded.ArrowBackIos,
                                contentDescription = null
                            )
                        }
                    },
                    rightContent = {
                        IconButton(onClick = { date = date.plusDays(1) }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Rounded.ArrowForwardIos,
                                contentDescription = null
                            )
                        }
                    },
                    mainContent = {
                        Text(
                            text = dateTimePattern.formatDate(date),
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                )
            }
        }
        val parentBackStackEntry = remember(navBackStackEntry) {
            appState.navHostController.getBackStackEntry(
                route = AppDestinations.Memories.All.route
            )
        }
        val viewModel = hiltViewModel<MemoriesViewModel>(viewModelStoreOwner = parentBackStackEntry)
        Content(modifier, viewModel, date)
    }

    @Composable
    private fun Content(
        modifier: Modifier = Modifier,
        viewModel: MemoriesViewModel,
        date: LocalDate
    ) {
        val asyncMemories by viewModel.memoriesState
        RequiredAsyncValueContent(
            asyncValue = asyncMemories
        ) { memories ->
            val imageViewerState = remember { ImageViewerState() }
            ImageViewer(
                modifier = modifier.fillMaxSize(),
                state = imageViewerState
            ) {
                LazyColumn(
                    modifier = modifier,
                    contentPadding = PaddingValues(all = 20.dp).alsoNavBar(),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    val dailyMemories = memories
                        .filter { it.associatedDate != null }
                        .filter { it.associatedDate!!.atStartOfDay() == date.atStartOfDay() }
                        .sortedByDescending { it.addedAt }
                    items(items = dailyMemories) { memory ->
                        ByteImage(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(500.dp)
                                .clip(RoundedCornerShape(20.dp))
                                .clickable { imageViewerState.showItem(memory.photoContent) },
                            byteContent = memory.photoContent
                        )
                    }
                }
            }
        }
    }
}