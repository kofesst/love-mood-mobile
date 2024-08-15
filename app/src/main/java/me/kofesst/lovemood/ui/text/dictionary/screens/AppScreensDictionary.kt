package me.kofesst.lovemood.ui.text.dictionary.screens

import android.content.Context
import me.kofesst.lovemood.R
import me.kofesst.lovemood.ui.text.ResourceText

class AppScreensDictionary(appContext: Context) {
    val aboutAppTitle = ResourceText(
        R.string.screen__app__about_app_title, appContext
    )
    val appSettingsCardTitle = ResourceText(
        R.string.screen__app__app_settings_card_title, appContext
    )
    val appSettingsCardText = ResourceText(
        R.string.screen__app__app_settings_card_text, appContext
    )
    val appTodosCardTitle = ResourceText(
        R.string.screen__app__app_todos_card_title, appContext
    )
    val appTodosCardText = ResourceText(
        R.string.screen__app__app_todos_card_text, appContext
    )
    val appVersionHistoryCardTitle = ResourceText(
        R.string.screen__app__app_version_history_card_title, appContext
    )
    val appVersionHistoryCardText = ResourceText(
        R.string.screen__app__app_version_history_card_text, appContext
    )
    
    val appTodosScreenTitle = ResourceText(
        R.string.screen__app__app_todos_screen_title, appContext
    )
    val todoStatus = ResourceText(
        R.string.screen__app__todo_status, appContext
    )
}