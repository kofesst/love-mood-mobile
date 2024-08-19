package me.kofesst.lovemood.ui.text.dictionary.screens

import android.content.Context

class ScreensDictionary(appContext: Context) {
    val userProfileForm = UserProfileFormScreenDictionary(appContext)
    val relationshipForm = RelationshipFormScreenDictionary(appContext)
    val memoriesList = MemoriesListScreenDictionary(appContext)
    val memoryForm = MemoryFormScreenDictionary(appContext)
    val memoriesCalendar = MemoriesCalendarScreenDictionary(appContext)
    val home = HomeScreenDictionary(appContext)
    val app = AppScreensDictionary(appContext)
}