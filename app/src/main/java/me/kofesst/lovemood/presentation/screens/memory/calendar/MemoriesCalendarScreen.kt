package me.kofesst.lovemood.presentation.screens.memory.calendar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import me.kofesst.android.lovemood.navigation.AppScreen
import me.kofesst.lovemood.app.AppDestinations
import me.kofesst.lovemood.app.LocalAppState
import me.kofesst.lovemood.app.dictionary
import me.kofesst.lovemood.core.models.PhotoMemory
import me.kofesst.lovemood.core.ui.components.calendar.CalendarCellDefaults
import me.kofesst.lovemood.core.ui.components.calendar.CalendarDayCell
import me.kofesst.lovemood.core.ui.components.calendar.CalendarDayCellContainer
import me.kofesst.lovemood.core.ui.components.calendar.LazyCalendarColumn
import me.kofesst.lovemood.core.ui.components.image.ByteImage
import me.kofesst.lovemood.core.ui.components.scaffold.NavigateUpIconButton
import me.kofesst.lovemood.core.ui.components.scaffold.SmallAppTopBar
import me.kofesst.lovemood.core.ui.utils.alsoNavBar
import me.kofesst.lovemood.core.ui.utils.alsoStatusBar
import me.kofesst.lovemood.presentation.screens.memory.list.MemoriesViewModel
import java.time.LocalDate

object MemoriesCalendarScreen : AppScreen() {
    private val ScreenCalendarDayCellsDefaults: CalendarCellDefaults
        @Composable get() = CalendarCellDefaults.defaults(
            containerColor = Color.Transparent,
            contentColor = Color.White
        )

    @Composable
    override fun ScreenContent(
        modifier: Modifier,
        navBackStackEntry: NavBackStackEntry
    ) {
        val appState = LocalAppState.current
        LaunchedEffect(Unit) {
            updateTopBar {
                SmallAppTopBar(
                    modifier = Modifier.fillMaxWidth(),
                    leftContent = {
                        NavigateUpIconButton { appState.navigateUp() }
                    },
                    mainContent = {
                        Text(
                            text = dictionary.screens.memoriesCalendar.screenTitle.string(),
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
        Content(modifier, viewModel)
    }

    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    private fun Content(
        modifier: Modifier = Modifier,
        viewModel: MemoriesViewModel
    ) {
        val asyncMemories by viewModel.memoriesState
        val associatedMemories = remember(asyncMemories) {
            asyncMemories.value?.associated() ?: emptyList()
        }
        LazyCalendarColumn(
            modifier = modifier.fillMaxSize(),
            contentPadding = PaddingValues(20.dp)
                .alsoNavBar()
                .alsoStatusBar(),
            verticalArrangement = Arrangement.spacedBy(space = 20.dp),
            dayCellContent = { date ->
                val dayMemories = remember(associatedMemories) {
                    associatedMemories.byDate(date)
                }
                if (dayMemories.isNotEmpty()) {
                    val appState = LocalAppState.current
                    DayCellWithMemory(
                        date = date,
                        memory = dayMemories.first(),
                        onClick = {
                            appState.navigate(
                                destination = AppDestinations.Memories.Daily,
                                argumentValues = arrayOf(
                                    AppDestinations.Memories.Daily.dateArgument
                                            to date
                                )
                            )
                        }
                    )
                } else {
                    CalendarDayCell(
                        defaults = ScreenCalendarDayCellsDefaults,
                        date = date
                    )
                }
            }
        )
    }

    @Composable
    private fun DayCellWithMemory(
        modifier: Modifier = Modifier,
        date: LocalDate,
        memory: PhotoMemory,
        onClick: () -> Unit
    ) {
        CalendarDayCellContainer(
            modifier = modifier,
            defaults = ScreenCalendarDayCellsDefaults,
            onClick = onClick
        ) {
            ByteImage(
                modifier = Modifier.size(ScreenCalendarDayCellsDefaults.containerSize),
                byteContent = memory.photoContent,
                colorFilter = ColorFilter.tint(
                    color = Color.Black.copy(alpha = 0.5f),
                    blendMode = BlendMode.Multiply
                )
            )
            Text(text = date.dayOfMonth.toString())
        }
    }
}