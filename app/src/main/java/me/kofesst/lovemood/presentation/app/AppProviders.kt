package me.kofesst.lovemood.presentation.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.staticCompositionLocalOf
import com.valentinilk.shimmer.Shimmer
import me.kofesst.lovemood.core.models.UserSettings
import me.kofesst.lovemood.features.date.DateTimePattern
import me.kofesst.lovemood.presentation.main.MainActivity
import me.kofesst.lovemood.ui.text.dictionary.AppDictionary

val LocalMainActivity = staticCompositionLocalOf<MainActivity> {
    error("MainActivity is not provided")
}

val LocalAppState = staticCompositionLocalOf<AppState> {
    error("AppState is not provided")
}

val LocalUserSettings = compositionLocalOf<UserSettings?> {
    error("UserSettings is not provided")
}

val LocalDateTimePattern = staticCompositionLocalOf<DateTimePattern> {
    error("DateTimePattern is not provided")
}

val LocalDictionary = staticCompositionLocalOf<AppDictionary> {
    error("AppDictionary is not provided")
}

val dictionary @Composable get() = LocalDictionary.current

val LocalShimmer = compositionLocalOf<Shimmer?> { null }