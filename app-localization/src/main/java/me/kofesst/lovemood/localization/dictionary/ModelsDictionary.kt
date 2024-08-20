package me.kofesst.lovemood.localization.dictionary

import android.content.Context
import me.kofesst.lovemood.localization.R
import me.kofesst.lovemood.localization.ResourceText

class ModelsDictionary(appContext: Context) {
    val shortMaleGenderName = ResourceText(
        R.string.models__short_male_gender_name, appContext
    )
    val shortFemaleGenderName = ResourceText(
        R.string.models__short_female_gender_name, appContext
    )
}