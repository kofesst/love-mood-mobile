package me.kofesst.lovemood.presentation.app

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import me.kofesst.lovemood.presentation.navigation.AppNavigation
import me.kofesst.lovemood.presentation.navigation.AppScreen
import me.kofesst.lovemood.presentation.navigation.AppScreenArgument

@Stable
data class AppState(
    val navHostController: NavHostController,
    val coroutineScope: CoroutineScope,
    val snackbarHostState: SnackbarHostState
) {
    private val screensFlow: Flow<AppScreen?>
        get() = navHostController.currentBackStackEntryFlow
            .map { backStackEntry ->
                val currentRoute = backStackEntry.destination.route
                AppNavigation.AllScreens.firstOrNull { screen ->
                    screen.route.equals(
                        currentRoute,
                        ignoreCase = true
                    )
                }
            }

    val currentScreenState: State<AppScreen?>
        @Composable get() = screensFlow.collectAsState(initial = null)

    fun showSnackbar(message: String) {
        coroutineScope.launch {
            snackbarHostState.showSnackbar(
                message = message,
                duration = SnackbarDuration.Short
            )
        }
    }

    fun navigateUp() {
        navHostController.navigateUp()
    }

    fun navigate(
        appScreen: AppScreen,
        clearBackStack: Boolean = false,
        vararg argumentValues: Pair<AppScreenArgument<*>, Any>
    ) {
        navHostController.navigate(appScreen.withArgs(*argumentValues)) {
            if (clearBackStack) {
                popUpTo(0) {
                    inclusive = true
                }
            }
        }
    }
}

@Composable
fun rememberAppState(
    navHostController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
) = remember {
    AppState(
        navHostController = navHostController,
        coroutineScope = coroutineScope,
        snackbarHostState = snackbarHostState
    )
}