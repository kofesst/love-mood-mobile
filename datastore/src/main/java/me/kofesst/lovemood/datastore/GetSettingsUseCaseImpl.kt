package me.kofesst.lovemood.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import me.kofesst.lovemood.core.models.UserSettings
import me.kofesst.lovemood.core.usecases.datastore.GetSettingsUseCase

data class GetSettingsUseCaseImpl(
    private val dataStore: DataStore<Preferences>
) : GetSettingsUseCase {
    override suspend fun invoke(): UserSettings {
        val savedSettings = dataStore.data.map { preferences ->
            val profileId = preferences[DatastoreConstants.PROFILE_ID_KEY]
            profileId?.takeIf { it >= 0 }?.let { userProfileId ->
                UserSettings(
                    userProfileId = userProfileId
                )
            }
        }.firstOrNull()
        val newSettings = UserSettings(
            userProfileId = null
        )
        return savedSettings ?: newSettings.also {
            dataStore.edit { preferences ->
                preferences[DatastoreConstants.PROFILE_ID_KEY] = newSettings.userProfileId ?: -1
            }
        }
    }
}
