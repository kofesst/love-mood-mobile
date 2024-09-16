package me.kofesst.lovemood.datastore

import androidx.datastore.preferences.core.intPreferencesKey

internal object DatastoreConstants {
    const val FILENAME = "user_settings"
    val PROFILE_ID_KEY = intPreferencesKey("profile_id")
    val RELATIONSHIP_ID_KEY = intPreferencesKey("relationship_id")
}