package me.kofesst.lovemood.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.kofesst.lovemood.async.SnapshotValue
import me.kofesst.lovemood.async.loadSnapshot
import me.kofesst.lovemood.core.interactor.memory.MemoryInteractor
import me.kofesst.lovemood.core.interactor.profile.UserProfileInteractor
import me.kofesst.lovemood.core.interactor.relationship.RelationshipInteractor
import me.kofesst.lovemood.core.models.PhotoMemory
import me.kofesst.lovemood.core.models.Profile
import me.kofesst.lovemood.core.models.Relationship
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val profileInteractor: UserProfileInteractor,
    private val relationshipInteractor: RelationshipInteractor,
    private val memoryInteractor: MemoryInteractor
) : ViewModel() {
    private val _profileFlow = MutableStateFlow(SnapshotValue<Profile>())
    val profileLoadingFlow = _profileFlow.asStateFlow()

    private val _relationshipFlow = MutableStateFlow(SnapshotValue<Relationship>())
    val relationshipLoadingFlow = _relationshipFlow.asStateFlow()

    private val _memoriesFlow = MutableStateFlow(SnapshotValue<List<PhotoMemory>>())
    val memoriesLoadingFlow = _memoriesFlow.asStateFlow()

    fun loadData() {
        viewModelScope.launch {
            // TODO Add handling of type of interactor result (success or failure)
            _profileFlow.loadSnapshot { profileInteractor.get().getOrNull() }
            _relationshipFlow.loadSnapshot { relationshipInteractor.get().getOrNull() }
            _memoriesFlow.loadSnapshot { memoryInteractor.getAll().getOrNull() }
        }
    }
}