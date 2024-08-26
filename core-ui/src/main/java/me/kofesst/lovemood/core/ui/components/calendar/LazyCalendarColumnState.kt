package me.kofesst.lovemood.core.ui.components.calendar

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import java.time.YearMonth

@Stable
class LazyCalendarColumnState(
    val lazyState: LazyListState,
    val range: CalendarRange
)

@Composable
fun rememberLazyCalendarColumnState(
    range: CalendarRange = DefaultCalendarRange,
    initialFirstVisibleYearMonth: YearMonth = YearMonth.now()
) = LazyCalendarColumnState(
    lazyState = rememberLazyListState(
        initialFirstVisibleItemIndex = range.getItemIndex(initialFirstVisibleYearMonth)
    ),
    range = range
)