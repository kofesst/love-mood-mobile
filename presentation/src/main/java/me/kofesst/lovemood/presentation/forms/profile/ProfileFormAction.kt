package me.kofesst.lovemood.presentation.forms.profile

import me.kofesst.lovemood.core.models.Profile
import me.kofesst.lovemood.presentation.forms.FormAction
import java.time.LocalDate

sealed class ProfileFormAction : FormAction<Profile, ProfileFormState> {
    data class UsernameChanged(private val value: String) : ProfileFormAction() {
        override fun applyToForm(currentForm: ProfileFormState): ProfileFormState {
            return currentForm.copy(username = value)
        }
    }

    @Suppress("ArrayInDataClass")
    data class AvatarContentChanged(private val value: ByteArray) : ProfileFormAction() {
        override fun applyToForm(currentForm: ProfileFormState): ProfileFormState {
            return currentForm.copy(avatarContent = value)
        }
    }

    data class DateOfBirthChanged(private val value: LocalDate) : ProfileFormAction() {
        override fun applyToForm(currentForm: ProfileFormState): ProfileFormState {
            return currentForm.copy(dateOfBirth = value)
        }
    }

    data object SubmitClicked : ProfileFormAction()
}