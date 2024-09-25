package me.kofesst.lovemood.localization.screens

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import me.kofesst.lovemood.localization.R
import me.kofesst.lovemood.localization.text.TextBuilder
import me.kofesst.lovemood.localization.text.buildResource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RelationshipFormLocalization @Inject constructor(
    @ApplicationContext private val context: Context
) {
    val loveStartDateStageTitle: TextBuilder =
        context buildResource R.string.relationship_form__love_start_date_stage_title

    val startDateLabel: TextBuilder =
        context buildResource R.string.relationship_form__start_date_label
}