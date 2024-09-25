package me.kofesst.lovemood.main

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import me.kofesst.lovemood.core.models.UserSession
import me.kofesst.lovemood.core.repository.SessionRepository
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val sessionRepository: SessionRepository
) : ViewModel() {
    private val _sessionState = mutableStateOf<UserSession?>(null)
    val sessionState: State<UserSession?> = _sessionState

    private val _preLoadedState = mutableStateOf(false)
    val preLoadedState: State<Boolean> = _preLoadedState

    fun preLoad() {
        viewModelScope.launch {
            val session = sessionRepository.restore()
            _sessionState.value = session
            _preLoadedState.value = true
        }
    }
}