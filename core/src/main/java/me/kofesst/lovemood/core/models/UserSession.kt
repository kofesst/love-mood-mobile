package me.kofesst.lovemood.core.models

/**
 * Данные сессии текущего пользователя.
 *
 * [profileId] - ID модели профиля текущего пользователя.
 *
 * [relationshipId] - ID модели отношений текущего пользователя.
 */
data class UserSession(
    val profileId: Int?,
    val relationshipId: Int?
)
