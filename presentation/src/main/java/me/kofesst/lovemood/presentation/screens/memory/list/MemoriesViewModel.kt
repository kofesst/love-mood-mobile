package me.kofesst.lovemood.presentation.screens.memory.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.kofesst.lovemood.async.SnapshotValue
import me.kofesst.lovemood.async.loadSnapshot
import me.kofesst.lovemood.core.interactor.memory.MemoryInteractor
import me.kofesst.lovemood.core.models.PhotoMemory
import javax.inject.Inject

@HiltViewModel
class MemoriesViewModel @Inject constructor(
    private val memoryInteractor: MemoryInteractor,
) : ViewModel() {
    private val _memoriesFlow = MutableStateFlow(SnapshotValue<List<PhotoMemory>>())
    val memoriesLoadingFlow = _memoriesFlow.asStateFlow()

    fun loadMemories() {
        viewModelScope.launch {
            _memoriesFlow.loadSnapshot { memoryInteractor.getAll().getOrNull() }
        }
    }
}