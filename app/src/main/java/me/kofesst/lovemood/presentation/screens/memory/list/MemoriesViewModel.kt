package me.kofesst.lovemood.presentation.screens.memory.list

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.kofesst.lovemood.core.models.PhotoMemory
import me.kofesst.lovemood.core.usecases.AppUseCases
import me.kofesst.lovemood.ui.async.AsyncValuesList
import me.kofesst.lovemood.ui.async.load
import javax.inject.Inject

@HiltViewModel
class MemoriesViewModel @Inject constructor(
    private val useCases: AppUseCases
) : ViewModel() {
    init {
        Log.d("LoveMood", "ViewModel initialized")
    }

    private val _memoriesState = mutableStateOf(AsyncValuesList<PhotoMemory>())
    val memoriesState: State<AsyncValuesList<PhotoMemory>> = _memoriesState

    fun loadMemories() {
        viewModelScope.launch(Dispatchers.IO) {
            _memoriesState.load {
                useCases.memories.readAll()
            }
        }
    }
}