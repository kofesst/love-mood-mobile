package me.kofesst.lovemood.core.repository

import me.kofesst.lovemood.core.models.UserSession

/**
 * Репозиторий данных сессии текущего пользователя.
 */
interface SessionRepository {
    /**
     * Возвращает данные сессии текущего пользователя.
     */
    suspend fun restore(): UserSession

    /**
     * Сохраняет данные сессии.
     */
    suspend fun save(data: UserSession)
}