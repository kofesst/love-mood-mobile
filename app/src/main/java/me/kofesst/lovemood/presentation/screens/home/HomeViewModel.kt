package me.kofesst.lovemood.presentation.screens.home

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.kofesst.lovemood.async.load
import me.kofesst.lovemood.core.models.PhotoMemory
import me.kofesst.lovemood.core.models.Profile
import me.kofesst.lovemood.core.models.Relationship
import me.kofesst.lovemood.core.usecases.AppUseCases
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCases: AppUseCases
) : ViewModel() {
    private val _userProfileState = mutableStateOf<me.kofesst.lovemood.async.AsyncValue<Profile>>(me.kofesst.lovemood.async.AsyncValue())
    val userProfileState: State<me.kofesst.lovemood.async.AsyncValue<Profile>> = _userProfileState

    private val _relationshipState = mutableStateOf<me.kofesst.lovemood.async.AsyncValue<Relationship>>(
        me.kofesst.lovemood.async.AsyncValue()
    )
    val relationshipState: State<me.kofesst.lovemood.async.AsyncValue<Relationship>> = _relationshipState

    private val _memoriesState = mutableStateOf<me.kofesst.lovemood.async.AsyncValuesList<PhotoMemory>>(
        me.kofesst.lovemood.async.AsyncValue()
    )
    val memoriesState: State<me.kofesst.lovemood.async.AsyncValuesList<PhotoMemory>> = _memoriesState

    fun loadData() {
        viewModelScope.launch(Dispatchers.IO) {
            val userSettings = useCases.dataStore.getSettings()
            Log.d("LoveMood", "Loaded user settings: $userSettings")
            val userProfileId = userSettings.userProfileId ?: return@launch
            _userProfileState.load {
                useCases.profile.readById(userProfileId)
            }
            Log.d("LoveMood", "Loaded profile: ${userProfileState.value}")
            _relationshipState.load {
                useCases.relationship.readByProfileId(userProfileId)
            }
            Log.d("LoveMood", "Loaded relationship: ${relationshipState.value}")
            _memoriesState.load {
                useCases.memories.readAll()
            }
            Log.d("LoveMood", "Loaded memories: ${memoriesState.value}")
        }
    }
}