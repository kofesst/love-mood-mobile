package me.kofesst.lovemood.presentation.screens.memory.calendar

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import me.kofesst.lovemood.core.models.PhotoMemory
import me.kofesst.lovemood.core.ui.components.calendar.CalendarCellDefaults
import me.kofesst.lovemood.core.ui.components.calendar.CalendarDayCell
import me.kofesst.lovemood.core.ui.components.calendar.CalendarDayCellContainer
import me.kofesst.lovemood.core.ui.components.calendar.LazyCalendarColumn
import me.kofesst.lovemood.core.ui.components.scaffold.SmallAppTopBar
import me.kofesst.lovemood.core.ui.utils.ByteArrayImage
import me.kofesst.lovemood.core.ui.utils.alsoNavBar
import me.kofesst.lovemood.core.ui.utils.alsoStatusBar
import me.kofesst.lovemood.presentation.app.LocalAppState
import me.kofesst.lovemood.presentation.app.dictionary
import me.kofesst.lovemood.presentation.navigation.AppNavigation
import me.kofesst.lovemood.presentation.screens.memory.list.MemoriesViewModel
import java.time.LocalDate

private val ScreenCalendarDayCellsDefaults: CalendarCellDefaults
    @Composable get() = CalendarCellDefaults.defaults(
        containerColor = Color.Transparent,
        contentColor = Color.White
    )

@OptIn(ExperimentalLayoutApi::class, ExperimentalFoundationApi::class)
@Composable
fun MemoriesCalendarScreen(
    modifier: Modifier = Modifier,
    navBackStackEntry: NavBackStackEntry
) {
    val appState = LocalAppState.current
    val parentBackStackEntry = remember(navBackStackEntry) {
        appState.navHostController.getBackStackEntry(
            route = AppNavigation.MemoriesScreen.route
        )
    }
    val viewModel = hiltViewModel<MemoriesViewModel>(
        viewModelStoreOwner = parentBackStackEntry
    )
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
                DayCellWithMemory(
                    date = date,
                    memory = dayMemories.first()
                )
            } else {
                CalendarDayCell(
                    defaults = ScreenCalendarDayCellsDefaults,
                    date = date
                )
            }
        },
        buildBeforeCalendar = {
            stickyHeader(key = "screen_header") {
                ScreenHeader()
            }
        }
    )
}

@Composable
private fun ScreenHeader(
    modifier: Modifier = Modifier,
) {
    val dictionary = dictionary.screens.memoriesCalendar
    val appState = LocalAppState.current
    SmallAppTopBar(
        modifier = modifier.fillMaxWidth(),
        leftContent = {
            IconButton(onClick = { appState.navigateUp() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                    contentDescription = null
                )
            }
        },
        mainContent = {
            Text(
                text = dictionary.screenTitle.string(),
                style = MaterialTheme.typography.titleLarge
            )
        }
    )
}

@Composable
private fun DayCellWithMemory(
    modifier: Modifier = Modifier,
    date: LocalDate,
    memory: PhotoMemory
) {
    CalendarDayCellContainer(
        modifier = modifier,
        defaults = ScreenCalendarDayCellsDefaults,
        onClick = {}
    ) {
        ByteArrayImage(
            content = memory.photoContent,
            size = ScreenCalendarDayCellsDefaults.containerSize,
            colorFilter = ColorFilter.tint(
                color = Color.Black.copy(alpha = 0.5f),
                blendMode = BlendMode.Multiply
            )
        )
        Text(text = date.dayOfMonth.toString())
    }
}