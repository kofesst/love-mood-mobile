package me.kofesst.lovemood.ui.text.dictionary.screens

import android.content.Context
import me.kofesst.lovemood.R
import me.kofesst.lovemood.presentation.forms.profile.ProfileFormDictionary
import me.kofesst.lovemood.presentation.forms.relationship.RelationshipFormDictionary
import me.kofesst.lovemood.ui.text.ResourceText

class RelationshipFormScreenDictionary(appContext: Context) {
    private val avatarPickerLabel = ResourceText(
        R.string.screens__relationship_form__avatar_picker_label, appContext
    )
    private val avatarPickerAction = ResourceText(
        R.string.screens__relationship_form__avatar_picker_action, appContext
    )
    private val usernameFieldLabel = ResourceText(
        R.string.screens__relationship_form__username_field_label, appContext
    )
    private val usernameFieldPlaceholder = ResourceText(
        R.string.screens__relationship_form__username_field_placeholder, appContext
    )
    private val genderFieldLabel = ResourceText(
        R.string.screens__relationship_form__gender_field_label, appContext
    )
    private val dateOfBirthFieldLabel = ResourceText(
        R.string.screens__relationship_form__date_of_birth_field_label, appContext
    )
    private val startDateFieldLabel = ResourceText(
        R.string.screens__relationship_form__start_date_field_label, appContext
    )

    val profileFormDictionary = ProfileFormDictionary(
        avatarPickerLabel = avatarPickerLabel,
        avatarPickerAction = avatarPickerAction,
        usernameFieldLabel = usernameFieldLabel,
        usernameFieldPlaceholder = usernameFieldPlaceholder,
        genderFieldLabel = genderFieldLabel,
        dateOfBirthFieldLabel = dateOfBirthFieldLabel
    )

    val relationshipFormDictionary = RelationshipFormDictionary(
        startDateFieldLabel = startDateFieldLabel
    )

    val createHeaderTitle = ResourceText(
        R.string.screens__relationship_form__create_header_title, appContext
    )
    val createHeaderSubtitle = ResourceText(
        R.string.screens__relationship_form__create_header_subtitle, appContext
    )
    val editHeader = ResourceText(
        R.string.screens__relationship_form__edit_header, appContext
    )
}