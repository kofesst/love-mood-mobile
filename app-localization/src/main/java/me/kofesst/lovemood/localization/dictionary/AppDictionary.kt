package me.kofesst.lovemood.localization.dictionary

import android.content.Context
import me.kofesst.lovemood.localization.R
import me.kofesst.lovemood.localization.ResourceText
import me.kofesst.lovemood.localization.dictionary.screens.ScreensDictionary

class AppDictionary(appContext: Context) {
    val notImplementedYet = ResourceText(
        R.string.not_implemented_yet, appContext
    )
    val appName = ResourceText(
        R.string.app_name, appContext
    )
    val appVersion = ResourceText(
        R.string.app_version, appContext
    )

    val errors = ErrorsDictionary(appContext)
    val dates = DatesDictionary(appContext)
    val screens = ScreensDictionary(appContext)
    val todos = AppTodosDictionary(appContext)
}