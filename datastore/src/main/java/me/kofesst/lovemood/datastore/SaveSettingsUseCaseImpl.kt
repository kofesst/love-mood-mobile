package me.kofesst.lovemood.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import me.kofesst.lovemood.core.models.UserSettings
import me.kofesst.lovemood.core.usecases.datastore.SaveSettingsUseCase

data class SaveSettingsUseCaseImpl(
    private val dataStore: DataStore<Preferences>
) : SaveSettingsUseCase {
    override suspend fun invoke(newSettings: UserSettings) {
        dataStore.edit { preferences ->
            preferences[DatastoreConstants.PROFILE_ID_KEY] = newSettings.userProfileId ?: -1
        }
    }
}
