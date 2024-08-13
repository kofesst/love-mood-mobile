package me.kofesst.lovemood.presentation.screens.app.todos

import me.kofesst.lovemood.ui.text.dictionary.AppTodosDictionary

class AppTodosRepository(dictionary: AppTodosDictionary) {
    val all
        get() = listOf(
            eventsTodo,
            memoriesTodo
        )

    private val eventsTodo = AppTodo(
        title = dictionary.eventsTitle,
        description = dictionary.eventsDescription,
        status = AppTodo.Status.InDevelop
    )
    private val memoriesTodo = AppTodo(
        title = dictionary.memoriesTitle,
        description = dictionary.memoriesDescription,
        status = AppTodo.Status.InDevelop
    )
}