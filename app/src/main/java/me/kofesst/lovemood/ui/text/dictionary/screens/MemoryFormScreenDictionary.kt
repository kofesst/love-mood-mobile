package me.kofesst.lovemood.ui.text.dictionary.screens

import android.content.Context
import me.kofesst.lovemood.R
import me.kofesst.lovemood.presentation.forms.memory.MemoryFormDictionary
import me.kofesst.lovemood.ui.text.ResourceText

class MemoryFormScreenDictionary(appContext: Context) {
    private val photoPickerLabel = ResourceText(
        R.string.screens__memory_form__photo_picker_label, appContext
    )
    private val photoPickerAction = ResourceText(
        R.string.screens__memory_form__photo_picker_action, appContext
    )
    private val isAssociatedWithDateLabel = ResourceText(
        R.string.screens__memory_form__is_associated_to_date_label, appContext
    )
    private val associatedDateFieldLabel = ResourceText(
        R.string.screens__memory_form__associated_date_field_label, appContext
    )
    val createHeaderTitle = ResourceText(
        R.string.screens__memory_form__create_header_title, appContext
    )
    val createHeaderSubtitle = ResourceText(
        R.string.screens__memory_form__create_header_subtitle, appContext
    )
    val editHeader = ResourceText(
        R.string.screens__memory_form__edit_header, appContext
    )

    val formDictionary = MemoryFormDictionary(
        photoPickerLabel = photoPickerLabel,
        photoPickerAction = photoPickerAction,
        isAssociatedWithDateLabel = isAssociatedWithDateLabel,
        associatedDateFieldLabel = associatedDateFieldLabel
    )
}