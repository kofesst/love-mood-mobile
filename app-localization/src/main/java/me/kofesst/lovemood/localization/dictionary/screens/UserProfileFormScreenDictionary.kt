package me.kofesst.lovemood.localization.dictionary.screens

import android.content.Context
import me.kofesst.lovemood.localization.R
import me.kofesst.lovemood.localization.ResourceText
import me.kofesst.lovemood.localization.dictionary.screens.forms.ProfileFormDictionary

class UserProfileFormScreenDictionary(appContext: Context) {
    private val avatarPickerLabel = ResourceText(
        R.string.screens__user_profile_form__avatar_picker_label, appContext
    )
    private val avatarPickerAction = ResourceText(
        R.string.screens__user_profile_form__avatar_picker_action, appContext
    )
    private val usernameFieldLabel = ResourceText(
        R.string.screens__user_profile_form__username_field_label, appContext
    )
    private val usernameFieldPlaceholder = ResourceText(
        R.string.screens__user_profile_form__username_field_placeholder, appContext
    )
    private val genderFieldLabel = ResourceText(
        R.string.screens__user_profile_form__gender_field_label, appContext
    )
    private val dateOfBirthFieldLabel = ResourceText(
        R.string.screens__user_profile_form__date_of_birth_field_label, appContext
    )

    val formDictionary = ProfileFormDictionary(
        avatarPickerLabel = avatarPickerLabel,
        avatarPickerAction = avatarPickerAction,
        usernameFieldLabel = usernameFieldLabel,
        usernameFieldPlaceholder = usernameFieldPlaceholder,
        genderFieldLabel = genderFieldLabel,
        dateOfBirthFieldLabel = dateOfBirthFieldLabel
    )

    val createHeaderTitle = ResourceText(
        R.string.screens__user_profile_form__create_header_title, appContext
    )
    val createHeaderSubtitle = ResourceText(
        R.string.screens__user_profile_form__create_header_subtitle, appContext
    )
    val editHeader = ResourceText(
        R.string.screens__user_profile_form__edit_header, appContext
    )
}