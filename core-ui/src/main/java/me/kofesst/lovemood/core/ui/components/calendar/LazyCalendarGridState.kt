package me.kofesst.lovemood.core.ui.components.calendar

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import java.time.YearMonth

@Stable
class LazyCalendarGridState(
    val lazyState: LazyListState,
    val range: CalendarRange
)

internal val DefaultCalendarRange: CalendarRange
    get() = CalendarRange(
        start = YearMonth.of(1991, 1),
        endInclusive = YearMonth.now()
    )

@Composable
fun rememberLazyCalendarGridState(
    range: CalendarRange = DefaultCalendarRange,
    initialFirstVisibleYearMonth: YearMonth = YearMonth.now()
) = LazyCalendarGridState(
    lazyState = rememberLazyListState(
        initialFirstVisibleItemIndex = range.getItemIndex(initialFirstVisibleYearMonth)
    ),
    range = range
)