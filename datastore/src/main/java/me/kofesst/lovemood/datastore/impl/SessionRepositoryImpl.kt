package me.kofesst.lovemood.datastore.impl

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import me.kofesst.lovemood.core.models.UserSession
import me.kofesst.lovemood.core.repository.SessionRepository
import me.kofesst.lovemood.datastore.DatastoreConstants
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class SessionRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : SessionRepository {
    companion object {
        private val DEFAULT_SESSION = UserSession(
            profileId = null,
            relationshipId = null
        )
    }

    override suspend fun restore(): UserSession {
        return dataStore.data.map { preferences ->
            val profileId = preferences[DatastoreConstants.PROFILE_ID_KEY]
            val relationshipId = preferences[DatastoreConstants.RELATIONSHIP_ID_KEY]
            UserSession(profileId, relationshipId)
        }.firstOrNull() ?: DEFAULT_SESSION
    }

    override suspend fun save(data: UserSession) {
        dataStore.edit { preferences ->
            with (preferences) {
                updateOrDelete(DatastoreConstants.PROFILE_ID_KEY, data.profileId)
                updateOrDelete(DatastoreConstants.RELATIONSHIP_ID_KEY, data.relationshipId)
            }
        }
    }

    private fun <T : Any> MutablePreferences.updateOrDelete(
        key: Preferences.Key<T>,
        value: T?
    ) {
        if (value == null) remove(key)
        else set(key, value)
    }
}