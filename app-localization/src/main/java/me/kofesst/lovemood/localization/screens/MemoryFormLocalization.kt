package me.kofesst.lovemood.localization.screens

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import me.kofesst.lovemood.localization.R
import me.kofesst.lovemood.localization.text.TextBuilder
import me.kofesst.lovemood.localization.text.buildResource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MemoryFormLocalization @Inject constructor(
    @ApplicationContext private val context: Context
) {
    val formTitle: TextBuilder =
        context buildResource R.string.memory_form__title

    val formSubtitle: TextBuilder =
        context buildResource R.string.memory_form__subtitle

    val selectPhotoLabel: TextBuilder =
        context buildResource R.string.memory_form__select_photo_label

    val removePhotoLabel: TextBuilder =
        context buildResource R.string.memory_form__remove_photo_label

    val associateWithDateLabel: TextBuilder =
        context buildResource R.string.memory_form__associate_with_date_label

    val associatedDateLabel: TextBuilder =
        context buildResource R.string.memory_form__associated_date_label
}