package me.kofesst.lovemood.core.ui.components.calendar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.FlowRowScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.YearMonth

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CalendarMonth(
    modifier: Modifier = Modifier,
    yearMonth: YearMonth,
    stubDayCellContent: @Composable FlowRowScope.(LocalDate) -> Unit,
    dayCellContent: @Composable FlowRowScope.(LocalDate) -> Unit
) {
    val firstDayOfMonth = remember { yearMonth.atDay(1) }
    val firstMonthDayOfWeek = remember { firstDayOfMonth.dayOfWeek.value }
    val lastDayOfMonth = remember { yearMonth.atDay(yearMonth.lengthOfMonth()) }
    val lastMonthDayOfWeek = remember { lastDayOfMonth.dayOfWeek.value }
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalArrangement = Arrangement.spacedBy(20.dp),
        maxItemsInEachRow = 7
    ) {
        for (stubDayOfMonth in 1 until firstMonthDayOfWeek) {
            stubDayCellContent(
                firstDayOfMonth.minusDays(stubDayOfMonth.toLong())
            )
        }
        for (dayOfMonth in 1..yearMonth.lengthOfMonth()) {
            dayCellContent(
                yearMonth.atDay(dayOfMonth)
            )
        }
        for (stubDayOfMonth in lastMonthDayOfWeek + 1..7) {
            stubDayCellContent(
                lastDayOfMonth.plusDays(stubDayOfMonth.toLong())
            )
        }
    }
}