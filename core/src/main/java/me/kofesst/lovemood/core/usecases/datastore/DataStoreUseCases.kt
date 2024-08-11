package me.kofesst.lovemood.core.usecases.datastore

/**
 * Use cases локального хранилища данных (datastore).
 */
interface DataStoreUseCases {
    val saveSettings: SaveSettingsUseCase
    val getSettings: GetSettingsUseCase
}