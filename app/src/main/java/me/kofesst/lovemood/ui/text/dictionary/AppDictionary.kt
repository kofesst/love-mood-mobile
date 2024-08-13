package me.kofesst.lovemood.ui.text.dictionary

import android.content.Context
import me.kofesst.lovemood.R
import me.kofesst.lovemood.ui.text.ResourceText
import me.kofesst.lovemood.ui.text.dictionary.screens.ScreensDictionary

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
    val models = ModelsDictionary(appContext)
    val dates = DatesDictionary(appContext)
    val screens = ScreensDictionary(appContext)
    val todos = AppTodosDictionary(appContext)
}