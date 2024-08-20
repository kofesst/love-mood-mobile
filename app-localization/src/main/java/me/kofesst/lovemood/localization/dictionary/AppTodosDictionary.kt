package me.kofesst.lovemood.localization.dictionary

import android.content.Context
import me.kofesst.lovemood.localization.R
import me.kofesst.lovemood.localization.ResourceText

class AppTodosDictionary(appContext: Context) {
    val appSettingsTitle = ResourceText(
        R.string.todos__app_settings_title, appContext
    )
    val appSettingsDescription = ResourceText(
        R.string.todos__app_settings_description, appContext
    )
    val eventsTitle = ResourceText(
        R.string.todos__events_title, appContext
    )
    val eventsDescription = ResourceText(
        R.string.todos__events_description, appContext
    )
    val memoriesTitle = ResourceText(
        R.string.todos__memories_title, appContext
    )
    val memoriesDescription = ResourceText(
        R.string.todos__memories_description, appContext
    )

    val inDevelopStatus = ResourceText(
        R.string.todos__status__in_develop, appContext
    )
    val releasedStatus = ResourceText(
        R.string.todos__status__released, appContext
    )
}