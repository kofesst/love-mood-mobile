package me.kofesst.lovemood.core.usecases.datastore

/**
 * Use cases локального хранилища данных (datastore).
 */
@Deprecated("Deprecated")
interface DataStoreUseCases {
    val saveSettings: SaveSettingsUseCase
    val getSettings: GetSettingsUseCase
}