package me.kofesst.lovemood.core.models

import me.kofesst.lovemood.features.date.DatePeriod
import java.time.LocalDate

/**
 * Модель отношений.
 *
 * [id] - ID модели.
 *
 * [userProfile] - профиль пользователя.
 *
 * [partnerProfile] - профиль партнёра.
 *
 * [startDate] - дата начала отношений.
 */
data class Relationship(
    val id: Int,
    val userProfile: Profile,
    val partnerProfile: Profile,
    val startDate: LocalDate
) {
    /**
     * Период длительности отношений.
     */
    val lovePeriod: DatePeriod
        get() = DatePeriod(startDate, LocalDate.now())
}
