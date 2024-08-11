package me.kofesst.lovemood.ui.text.dictionary

import android.content.Context
import androidx.compose.runtime.Composable
import me.kofesst.lovemood.R
import me.kofesst.lovemood.core.models.Gender
import me.kofesst.lovemood.presentation.app.LocalDictionary
import me.kofesst.lovemood.ui.text.ResourceText

class ModelsDictionary(appContext: Context) {
    val shortMaleGenderName = ResourceText(
        R.string.models__short_male_gender_name, appContext
    )
    val shortFemaleGenderName = ResourceText(
        R.string.models__short_female_gender_name, appContext
    )
}

val Gender.shortLocalizedName
    @Composable get() = when (this) {
        Gender.Male -> LocalDictionary.current.models.shortMaleGenderName
        Gender.Female -> LocalDictionary.current.models.shortFemaleGenderName
    }