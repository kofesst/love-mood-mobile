package me.kofesst.lovemood.localization.dictionary.screens

import android.content.Context
import me.kofesst.lovemood.localization.R
import me.kofesst.lovemood.localization.ResourceText

class AppScreensDictionary(appContext: Context) {
    val bottomBarTitle = ResourceText(
        R.string.screens__app__bottom_bar_title, appContext
    )
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
    val appWidgetCardTitle = ResourceText(
        R.string.screen__app__app_widget_card_title, appContext
    )
    val appWidgetCardText = ResourceText(
        R.string.screen__app__app_widget_card_text, appContext
    )

    val appTodosScreenTitle = ResourceText(
        R.string.screen__app__app_todos_screen_title, appContext
    )
    val todoStatus = ResourceText(
        R.string.screen__app__todo_status, appContext
    )
}