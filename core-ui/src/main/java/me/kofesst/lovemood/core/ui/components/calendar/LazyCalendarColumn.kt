@file:OptIn(ExperimentalLayoutApi::class)

package me.kofesst.lovemood.core.ui.components.calendar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.FlowRowScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.YearMonth

/**
 * LazyColumn в виде календаря, где сверху вниз
 * отображается календарь каждого месяца.
 *
 * [modifier] - модификатор компонента.
 *
 * [state] - состояние компонента.
 *
 * [contentPadding] - внутренние отступы.
 *
 * [horizontalAlignment] - горизонтальное выравнивание.
 *
 * [verticalArrangement] - вертикальное расположение.
 *
 * [monthTitle] - компонент заголовка месяца, находящийся перед
 * каждым календарём.
 *
 * [stubDayCellContent] - компонент дня-заглушки для дней,
 * которые не находятся в текущем месяце, например:
 *
 * ```
 *
 * | пн | вт | ср | чт | пт | сб | вс |
 * | xx | xx | xx | xx | xx |  1 |  2 |
 * |  3 |  4 |  5 |  6 |  7 |  8 |  9 |
 *                 ...
 * | 29 | 30 | xx | xx | xx | xx | xx |
 *
 * xx - день-заглушка.
 * Остальные дни - дни месяца. Их компонент задаётся в параметре dayCellContent.
 * ```
 *
 * [dayCellContent] - компонент для дня месяца.
 *
 * [buildBeforeCalendar] - контент компонента перед календарём.
 *
 * [buildAfterCalendar] - контент компонента после календаря.
 */
@Composable
fun LazyCalendarColumn(
    modifier: Modifier = Modifier,
    state: LazyCalendarColumnState = rememberLazyCalendarColumnState(),
    contentPadding: PaddingValues = PaddingValues(all = 0.dp),
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    monthTitle: @Composable (YearMonth) -> Unit = { yearMonth ->
        CalendarMonthTitle(yearMonth = yearMonth)
    },
    stubDayCellContent: @Composable FlowRowScope.(LocalDate) -> Unit = {
        CalendarDayCellStub()
    },
    dayCellContent: @Composable FlowRowScope.(LocalDate) -> Unit = {
        CalendarDayCell(date = it)
    },
    buildBeforeCalendar: LazyListScope.() -> Unit = {},
    buildAfterCalendar: LazyListScope.() -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        state = state.lazyState,
        contentPadding = contentPadding,
        horizontalAlignment = horizontalAlignment,
        verticalArrangement = verticalArrangement
    ) {
        buildBeforeCalendar()
        state.range.forEach { month ->
            monthItemContent(
                month = month,
                yearMonthTitle = {
                    monthTitle(month)
                },
                stubDayCellContent = stubDayCellContent,
                dayCellContent = dayCellContent
            )
        }
        buildAfterCalendar()
    }
}

private fun LazyListScope.monthItemContent(
    month: YearMonth,
    yearMonthTitle: @Composable () -> Unit,
    stubDayCellContent: @Composable FlowRowScope.(LocalDate) -> Unit,
    dayCellContent: @Composable FlowRowScope.(LocalDate) -> Unit
) {
    item(key = "month_cell_${month.year}_${month.monthValue}") {
        MonthContent(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            yearMonth = month,
            yearMonthTitle = yearMonthTitle,
            stubDayCellContent = stubDayCellContent,
            dayCellContent = dayCellContent
        )
    }
}

@Composable
private fun MonthContent(
    modifier: Modifier = Modifier,
    yearMonth: YearMonth,
    yearMonthTitle: @Composable () -> Unit,
    stubDayCellContent: @Composable FlowRowScope.(LocalDate) -> Unit,
    dayCellContent: @Composable FlowRowScope.(LocalDate) -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp) // todo replace with defaults
    ) {
        yearMonthTitle()
        MonthFlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            yearMonth = yearMonth,
            stubDayCellContent = stubDayCellContent,
            dayCellContent = dayCellContent
        )
    }
}

/**
 * Заголовок календаря месяца.
 *
 * [modifier] - модификатор компонента.
 *
 * [yearMonth] - месяц.
 *
 * [titleProducer] - функция, производящая название
 * месяца из класса [YearMonth].
 *
 * [titleModifier] - функция, модифицирующая название месяца.
 *
 * [style] - стиль текста.
 *
 * [textAlign] - выравнивание текста.
 *
 * [color] - цвет текста.
 */
@Composable
fun CalendarMonthTitle(
    modifier: Modifier = Modifier,
    yearMonth: YearMonth,
    titleProducer: (YearMonth) -> String = {
        buildString {
            append(
                it.month.getDisplayName(
                    java.time.format.TextStyle.FULL_STANDALONE,
                    java.util.Locale.getDefault()
                )
            )
            append(" | ")
            append(it.year.toString())
        }
    },
    titleModifier: (String) -> String = { title ->
        title.replaceFirstChar { it.uppercaseChar() }
    },
    style: TextStyle = MaterialTheme.typography.titleLarge,
    textAlign: TextAlign = TextAlign.Start,
    color: Color = MaterialTheme.colorScheme.primary
) {
    Text(
        modifier = modifier,
        text = titleModifier(titleProducer(yearMonth)),
        style = style,
        textAlign = textAlign,
        color = color
    )
}

@Composable
private fun MonthFlowRow(
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

/**
 * Ячейка дня месяца.
 *
 * [defaults] - настройки ячейки.
 *
 * [date] - дата текущего дня.
 */
@Composable
fun CalendarDayCell(
    defaults: CalendarCellDefaults = CalendarCellDefaults.defaults(),
    date: LocalDate
) {
    CalendarDayCellContainer(defaults = defaults) {
        Text(text = date.dayOfMonth.toString())
    }
}

/**
 * Кликабельная ячейка дня месяца.
 *
 * [defaults] - настройки ячейки.
 *
 * [date] - дата текущего дня.
 *
 * [onClick] - коллбек функция при нажатии на ячейку.
 */
@Composable
fun CalendarDayCell(
    defaults: CalendarCellDefaults = CalendarCellDefaults.defaults(),
    date: LocalDate,
    onClick: () -> Unit
) {
    CalendarDayCellContainer(
        defaults = defaults,
        onClick = onClick
    ) {
        Text(text = date.dayOfMonth.toString())
    }
}

/**
 * Ячейка дня-заглушки.
 *
 * [defaults] - настройки ячейки.
 */
@Composable
fun CalendarDayCellStub(
    defaults: CalendarCellDefaults = CalendarCellDefaults.stubDefaults()
) {
    CalendarDayCellContainer(
        defaults = defaults,
        content = {}
    )
}


/**
 * Базовый контейнер ячейки дня или дня-заглушки.
 *
 * [defaults] - настройки ячейки.
 *
 * [content] - внутренний контент ячейки.
 */
@Composable
fun CalendarDayCellContainer(
    modifier: Modifier = Modifier,
    defaults: CalendarCellDefaults,
    content: @Composable BoxScope.() -> Unit
) {
    Surface(
        modifier = modifier.size(defaults.containerSize),
        color = defaults.containerColor,
        contentColor = defaults.contentColor,
        shape = defaults.containerShape
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            content(this)
        }
    }
}

/**
 * Кликабельный базовый контейнер ячейки дня или дня-заглушки.
 *
 * [defaults] - настройки ячейки.
 *
 * [onClick] - коллбек функция при нажатии на ячейку.
 *
 * [content] - внутренний контент ячейки.
 */
@Composable
fun CalendarDayCellContainer(
    modifier: Modifier = Modifier,
    defaults: CalendarCellDefaults = CalendarCellDefaults.defaults(),
    onClick: () -> Unit,
    content: @Composable BoxScope.() -> Unit
) {
    Surface(
        modifier = Modifier.size(defaults.containerSize),
        color = defaults.containerColor,
        contentColor = defaults.contentColor,
        shape = defaults.containerShape,
        onClick = onClick
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            content(this)
        }
    }
}