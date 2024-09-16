package me.kofesst.lovemood.presentation.forms.profile

import me.kofesst.lovemood.core.models.Gender
import me.kofesst.lovemood.core.models.Profile
import me.kofesst.lovemood.presentation.forms.FormAction

sealed class ProfileFormAction : FormAction<Profile, ProfileFormState> {
    data class UsernameChanged(private val value: String) : ProfileFormAction() {
        override fun applyToForm(currentForm: ProfileFormState): ProfileFormState {
            return currentForm.copy(username = value)
        }
    }

    data class GenderChanged(private val value: Gender) : ProfileFormAction() {
        override fun applyToForm(currentForm: ProfileFormState): ProfileFormState {
            return currentForm.copy(gender = value)
        }
    }

    @Suppress("ArrayInDataClass")
    data class AvatarContentChanged(private val value: ByteArray) : ProfileFormAction() {
        override fun applyToForm(currentForm: ProfileFormState): ProfileFormState {
            return currentForm.copy(avatarContent = value)
        }
    }

    data class DateOfBirthChanged(private val value: String) : ProfileFormAction() {
        override fun applyToForm(currentForm: ProfileFormState): ProfileFormState {
            return currentForm.copy(dateOfBirth = value)
        }
    }

    data object SubmitClicked : ProfileFormAction()
}