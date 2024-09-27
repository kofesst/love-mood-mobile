package me.kofesst.lovemood.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.NavBackStackEntry
import me.kofesst.android.lovemood.navigation.AppDestination
import me.kofesst.lovemood.localization.AppLocalization

val LocalAppState = staticCompositionLocalOf<AppState> {
    error("App state is not provided")
}

val LocalMainViewModelStoreOwner = staticCompositionLocalOf<ViewModelStoreOwner> {
    error("Main viewModel store owner is not provided")
}

@Composable
internal inline fun <reified T : ViewModel> appViewModel(): T {
    val owner = LocalMainViewModelStoreOwner.current
    return hiltViewModel(owner)
}

@Composable
internal inline fun <reified T : ViewModel> NavBackStackEntry.parentViewModel(
    parentDestination: AppDestination
): T {
    val appState = LocalAppState.current
    val parentBackStackEntry = remember(this) {
        appState.navHostController.getBackStackEntry(
            route = parentDestination.route
        )
    }
    return hiltViewModel(parentBackStackEntry)
}

val LocalLocalization = staticCompositionLocalOf<AppLocalization> {
    error("Localization is not provided")
}

internal val localization: AppLocalization
    @Composable
    @ReadOnlyComposable
    get() = LocalLocalization.current