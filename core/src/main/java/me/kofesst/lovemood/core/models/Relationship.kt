package me.kofesst.lovemood.core.models

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
)
