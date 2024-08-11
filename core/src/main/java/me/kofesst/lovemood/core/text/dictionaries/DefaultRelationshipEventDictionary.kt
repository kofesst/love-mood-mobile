package me.kofesst.lovemood.core.text.dictionaries

import me.kofesst.lovemood.core.text.AppTextHolder

/**
 * Словарь стандартный событий в отношениях.
 *
 * [startDateYearlyEventName] - название события, когда
 * с начала отношений проходит N количество лет.
 *
 * [startDateMonthlyEventName] - название события, когда
 * с начала отношений проходит N количество месяцев.
 */
interface DefaultRelationshipEventDictionary {
    val startDateYearlyEventName: AppTextHolder
    val startDateMonthlyEventName: AppTextHolder
}