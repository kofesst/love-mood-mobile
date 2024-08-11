package me.kofesst.lovemood.core.usecases.datastore

import me.kofesst.lovemood.core.models.UserSettings

/**
 * Use case для получения настроек пользователя.
 *
 * Если настройки ещё не были сохранены, сохраняет
 * и возвращает стандартные настройки.
 */
interface GetSettingsUseCase {
    suspend operator fun invoke(): UserSettings
}