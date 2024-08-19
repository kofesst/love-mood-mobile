package me.kofesst.lovemood.ui.text.dictionary

import android.content.Context
import androidx.compose.runtime.Composable
import me.kofesst.lovemood.R
import me.kofesst.lovemood.app.LocalDictionary
import me.kofesst.lovemood.presentation.screens.app.todos.AppTodo
import me.kofesst.lovemood.ui.text.ResourceText

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

val AppTodo.Status.uiText: String
    @Composable get() = with(LocalDictionary.current.todos) {
        when (this@uiText) {
            is AppTodo.Status.InDevelop -> inDevelopStatus.string()
            is AppTodo.Status.Released -> releasedStatus.string("%app_version%", appVersion)
        }
    }