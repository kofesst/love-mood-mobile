package me.kofesst.lovemood.core.usecases.datastore

import me.kofesst.lovemood.core.models.UserSettings

/**
 * Use case сохранения настроек пользователя.
 */
@Deprecated("Deprecated")
interface SaveSettingsUseCase {
    suspend operator fun invoke(newSettings: UserSettings)
}