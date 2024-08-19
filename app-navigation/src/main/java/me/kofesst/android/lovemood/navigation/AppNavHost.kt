package me.kofesst.android.lovemood.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

/**
 * Хост навигации приложения.
 *
 * [modifier] - модификатор компонента.
 *
 * [screensModifier] - модификатор каждого экрана.
 *
 * [navHostController] - контроллер навигации.
 *
 * [startDestination] - стартовый пункт назначения приложения.
 *
 * [destinationsList] - все пункты назначения приложения.
 */
@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    screensModifier: Modifier = Modifier,
    navHostController: NavHostController,
    startDestination: AppDestination,
    destinationsList: List<AppDestination>
) {
    NavHost(
        modifier = modifier,
        navController = navHostController,
        startDestination = startDestination.route
    ) {
        destinationsList.forEach { destination ->
            buildDestination(
                screenModifier = screensModifier,
                destination = destination
            )
        }
    }
}

private fun NavGraphBuilder.buildDestination(
    screenModifier: Modifier,
    destination: AppDestination
) {
    composable(
        route = destination.route,
        arguments = destination.arguments.asComposeArguments()
    ) { navBackStackEntry ->
        destination.screen.ScreenContent(
            modifier = screenModifier,
            navBackStackEntry = navBackStackEntry
        )
    }
}