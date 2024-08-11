package me.kofesst.lovemood.core.models.events.defaults

import me.kofesst.lovemood.core.models.Relationship
import me.kofesst.lovemood.core.models.events.MonthlyRelationshipEvent
import me.kofesst.lovemood.core.models.events.YearlyRelationshipEvent
import me.kofesst.lovemood.core.text.dictionaries.DefaultRelationshipEventDictionary

/**
 * Описание стандартных событий в отношениях.
 */
data class RelationshipEventDefaults(
    private val eventDictionary: DefaultRelationshipEventDictionary
) {
    /**
     * Событие, когда с начала отношений проходит N количество лет.
     */
    fun Relationship.startDateYearlyEvent() = object : YearlyRelationshipEvent(
        dayOfMonth = this.startDate.dayOfMonth,
        monthValue = this.startDate.monthValue,
        id = -1,
        isDefault = true,
        relationship = this,
        nameHolder = eventDictionary.startDateYearlyEventName
    ) {}

    /**
     * Событие, когда с начала отношений проходит N количество месяцев.
     */
    fun Relationship.startDateMonthlyEvent() = object : MonthlyRelationshipEvent(
        dayOfMonth = this.startDate.dayOfMonth,
        id = -1,
        isDefault = true,
        relationship = this,
        nameHolder = eventDictionary.startDateMonthlyEventName
    ) {}
}