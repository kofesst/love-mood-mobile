package me.kofesst.lovemood.core.models.events

import me.kofesst.lovemood.core.models.Relationship
import me.kofesst.lovemood.core.text.AppTextHolder
import java.time.LocalDate
import java.time.Period

/**
 * Модель события, происходящего раз в год.
 *
 * [dayOfMonth] - день месяца.
 *
 * [monthValue] - месяц.
 */
open class YearlyRelationshipEvent(
    dayOfMonth: Int,
    val monthValue: Int,
    override val id: Int,
    override val isDefault: Boolean,
    override val nameHolder: AppTextHolder,
    override val relationship: Relationship
) : MonthlyRelationshipEvent(dayOfMonth, id, isDefault, nameHolder, relationship) {
    override fun getRemaining(): Period {
        val now = LocalDate.now()
        if (now.dayOfMonth == dayOfMonth && now.monthValue == monthValue) return Period.ZERO
        val eventMonthDate = if (now.dayOfMonth > dayOfMonth) {
            now.plusMonths(1)
        } else {
            now
        }
        val eventYearDate = if (now.monthValue > monthValue) {
            eventMonthDate.plusYears(1)
        } else {
            eventMonthDate
        }
        val eventDate = LocalDate.of(eventYearDate.year, eventYearDate.monthValue, dayOfMonth)
        return Period.between(now, eventDate)
    }
}