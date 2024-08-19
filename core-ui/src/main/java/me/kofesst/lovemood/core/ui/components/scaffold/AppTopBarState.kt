package me.kofesst.lovemood.core.ui.components.scaffold

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf

@Stable
class AppTopBarState {
    private val _contentState: MutableState<ComposableContent> = mutableStateOf(value = {})
    val contentState: State<ComposableContent> = _contentState

    fun setContent(content: ComposableContent) {
        _contentState.value = content
    }
}