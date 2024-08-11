package me.kofesst.lovemood.core.models.events

import me.kofesst.lovemood.core.models.Relationship
import me.kofesst.lovemood.core.text.AppTextHolder
import java.time.LocalDate
import java.time.Period

/**
 * Модель события, происходящего раз в месяц.
 *
 * [dayOfMonth] - день события.
 */
open class MonthlyRelationshipEvent(
    val dayOfMonth: Int,
    override val id: Int,
    override val isDefault: Boolean,
    override val nameHolder: AppTextHolder,
    override val relationship: Relationship
) : RelationshipEvent() {
    override fun getRemaining(): Period {
        val now = LocalDate.now()
        if (now.dayOfMonth == dayOfMonth) return Period.ZERO
        val eventMonthDate = if (now.dayOfMonth > dayOfMonth) {
            now.plusMonths(1)
        } else {
            now
        }
        val eventDate = LocalDate.of(eventMonthDate.year, eventMonthDate.monthValue, dayOfMonth)
        return Period.between(now, eventDate)
    }
}
