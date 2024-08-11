package me.kofesst.lovemood.datastore

import androidx.datastore.preferences.core.intPreferencesKey

object DatastoreConstants {
    const val FILENAME = "user_settings"
    val PROFILE_ID_KEY = intPreferencesKey("profile_id")
}