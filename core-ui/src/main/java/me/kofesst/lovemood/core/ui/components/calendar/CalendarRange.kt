package me.kofesst.lovemood.core.ui.components.calendar

import java.time.YearMonth

val a = IntRange

class CalendarIterator internal constructor(
    start: YearMonth,
    endInclusive: YearMonth
) : Iterator<YearMonth> {
    private val finalElement = endInclusive
    private var hasNext: Boolean = start <= endInclusive
    private var next: YearMonth = start

    override fun hasNext(): Boolean = hasNext

    override fun next(): YearMonth {
        val nextValue = next
        if (nextValue == finalElement) {
            if (!hasNext) throw NoSuchElementException()
            hasNext = false
        } else {
            next = next.plusMonths(1)
        }
        return nextValue
    }
}

class CalendarRange(
    override val start: YearMonth,
    override val endInclusive: YearMonth
) : ClosedRange<YearMonth>, OpenEndRange<YearMonth>, Iterable<YearMonth> {
    override val endExclusive: YearMonth = endInclusive.minusMonths(1)

    @Suppress("ConvertTwoComparisonsToRangeCheck")
    override fun contains(value: YearMonth): Boolean {
        return start <= value && endInclusive >= value
    }

    override fun isEmpty(): Boolean {
        return start > endInclusive
    }

    override fun iterator(): Iterator<YearMonth> {
        return CalendarIterator(start, endInclusive)
    }

    fun itemsCount(): Int {
        return start.monthValue + endInclusive.monthValue + 12 * (endInclusive.year - start.year)
    }

    fun getItemIndex(item: YearMonth): Int {
        return item.monthValue - start.monthValue + 12 * (item.year - start.year)
    }
}