package me.kofesst.lovemood.presentation.screens.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import me.kofesst.lovemood.async.AsyncValue
import me.kofesst.lovemood.async.AsyncValuesList
import me.kofesst.lovemood.async.load
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
    private val _userProfileState = mutableStateOf<AsyncValue<Profile>>(AsyncValue())
    val userProfileState: State<AsyncValue<Profile>> = _userProfileState

    private val _relationshipState = mutableStateOf<AsyncValue<Relationship>>(AsyncValue())
    val relationshipState: State<AsyncValue<Relationship>> = _relationshipState

    private val _memoriesState = mutableStateOf<AsyncValuesList<PhotoMemory>>(AsyncValue())
    val memoriesState: State<AsyncValuesList<PhotoMemory>> = _memoriesState

    fun loadData() {
        viewModelScope.launch {
            // TODO Add handling of type of interactor result (success or failure)
            _userProfileState.load { profileInteractor.get().getOrNull() }
            _relationshipState.load { relationshipInteractor.get().getOrNull() }
            _memoriesState.load { memoryInteractor.getAll().getOrNull() }
        }
    }
}