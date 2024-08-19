package me.kofesst.lovemood.presentation.screens.app.todos

import me.kofesst.lovemood.ui.text.dictionary.AppTodosDictionary

class AppTodosRepository(dictionary: AppTodosDictionary) {
    val all
        get() = listOf(
            appSettingsTodo,
            eventsTodo,
            memoriesTodo
        )

    private val appSettingsTodo = AppTodo(
        title = dictionary.appSettingsTitle,
        description = dictionary.appSettingsDescription,
        status = AppTodo.Status.InDevelop
    )
    private val eventsTodo = AppTodo(
        title = dictionary.eventsTitle,
        description = dictionary.eventsDescription,
        status = AppTodo.Status.InDevelop
    )
    private val memoriesTodo = AppTodo(
        title = dictionary.memoriesTitle,
        description = dictionary.memoriesDescription,
        status = AppTodo.Status.Released(appVersion = "0.0.2")
    )
}