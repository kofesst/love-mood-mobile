package me.kofesst.lovemood.presentation.main

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import me.kofesst.lovemood.core.models.UserSettings
import me.kofesst.lovemood.core.usecases.AppUseCases
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCases: AppUseCases
) : ViewModel() {
    private val _settingsState = mutableStateOf<UserSettings?>(null)
    val settingsState: State<UserSettings?> = _settingsState

    private val _preLoadedState = mutableStateOf(false)
    val preLoadedState: State<Boolean> = _preLoadedState

    fun preLoad() {
        viewModelScope.launch {
            val settings = useCases.dataStore.getSettings()
            _settingsState.value = settings

            _preLoadedState.value = true
        }
    }
}