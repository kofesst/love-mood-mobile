package me.kofesst.lovemood.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import me.kofesst.lovemood.presentation.app.LocalAppState

/**
 * NavHost компонент приложения.
 *
 * [modifier] - модификатор компонента.
 *
 * [baseModifier] - базовый модификатор всех экранов.
 *
 * [userHasProfile] - есть ли у пользователя созданный профиль.
 */
@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    baseModifier: Modifier,
    userHasProfile: Boolean
) {
    val appState = LocalAppState.current
    NavHost(
        modifier = modifier,
        navController = appState.navHostController,
        startDestination = AppNavigation.getStartDestination(userHasProfile)
    ) {
        AppNavigation.AllScreens.forEach { screen ->
            buildScreen(
                baseModifier = baseModifier,
                appScreen = screen
            )
        }
    }
}

/**
 * Генерирует экран приложения внутри навигации приложения.
 *
 * [baseModifier] - модификатор экрана.
 *
 * [appScreen] - объект экрана приложения.
 */
private fun NavGraphBuilder.buildScreen(
    baseModifier: Modifier,
    appScreen: AppScreen
) {
    composable(
        route = appScreen.route,
        arguments = appScreen.arguments.asComposeArguments()
    ) { navBackStackEntry ->
        appScreen.ComposableContent(baseModifier, navBackStackEntry)
    }
}