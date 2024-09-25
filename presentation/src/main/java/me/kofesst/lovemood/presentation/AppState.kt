package me.kofesst.lovemood.presentation

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
import me.kofesst.android.lovemood.navigation.AppDestination
import me.kofesst.android.lovemood.navigation.DestinationArgument

@Stable
data class AppState(
    val navHostController: NavHostController,
    val coroutineScope: CoroutineScope,
    val snackbarHostState: SnackbarHostState
) {
    private val destinationsFlow: Flow<AppDestination?>
        get() = navHostController.currentBackStackEntryFlow
            .map { backStackEntry ->
                val currentRoute = backStackEntry.destination.route
                Destinations.getAllDestinations().firstOrNull { screen ->
                    screen.route.equals(
                        currentRoute,
                        ignoreCase = true
                    )
                }
            }

    val currentDestinationState: State<AppDestination?>
        @Composable get() = destinationsFlow.collectAsState(initial = null)

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
        destination: AppDestination,
        clearBackStack: Boolean = false,
        vararg argumentValues: Pair<DestinationArgument<*>, Any>
    ) {
        navHostController.navigate(destination.withArgs(*argumentValues)) {
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