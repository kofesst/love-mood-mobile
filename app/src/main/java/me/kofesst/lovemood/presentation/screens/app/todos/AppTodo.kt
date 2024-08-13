package me.kofesst.lovemood.presentation.screens.app.todos

import me.kofesst.lovemood.core.text.AppTextHolder

data class AppTodo(
    val title: AppTextHolder,
    val description: AppTextHolder,
    val status: Status
) {
    sealed interface Status {
        data object InDevelop : Status
        class Released(val appVersion: String) : Status
    }
}
