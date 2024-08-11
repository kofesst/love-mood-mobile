package me.kofesst.lovemood.presentation.screens.home

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import me.kofesst.lovemood.core.models.Profile
import me.kofesst.lovemood.core.models.Relationship
import me.kofesst.lovemood.core.usecases.AppUseCases
import me.kofesst.lovemood.ui.async.AsyncValue
import me.kofesst.lovemood.ui.async.load
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCases: AppUseCases
) : ViewModel() {
    private val _userProfileState = mutableStateOf<AsyncValue<Profile>>(AsyncValue())
    val userProfileState: State<AsyncValue<Profile>> = _userProfileState

    private val _relationshipState = mutableStateOf<AsyncValue<Relationship>>(AsyncValue())
    val relationshipState: State<AsyncValue<Relationship>> = _relationshipState

    fun loadData() {
        viewModelScope.launch {
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
        }
    }
}