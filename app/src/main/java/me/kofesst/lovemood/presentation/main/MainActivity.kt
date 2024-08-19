package me.kofesst.lovemood.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import me.kofesst.android.lovemood.navigation.AppDestination
import me.kofesst.android.lovemood.navigation.AppMainDestination
import me.kofesst.android.lovemood.navigation.AppNavHost
import me.kofesst.lovemood.app.AppDestinations
import me.kofesst.lovemood.core.models.UserSettings
import me.kofesst.lovemood.core.ui.components.scaffold.AppScaffold
import me.kofesst.lovemood.features.date.DateTimePattern
import me.kofesst.lovemood.presentation.app.LocalAppState
import me.kofesst.lovemood.presentation.app.LocalDateTimePattern
import me.kofesst.lovemood.presentation.app.LocalDictionary
import me.kofesst.lovemood.presentation.app.LocalMainActivity
import me.kofesst.lovemood.presentation.app.LocalUserSettings
import me.kofesst.lovemood.presentation.app.rememberAppState
import me.kofesst.lovemood.ui.text.dictionary.AppDictionary
import me.kofesst.lovemood.ui.theme.LoveMoodMobileTheme
import me.kofesst.lovemood.ui.theme.WithShimmerTheme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var dateTimePattern: DateTimePattern

    private val _bottomBarVisibleState = mutableStateOf(false)

    private fun expandBottomBar() {
        _bottomBarVisibleState.value = true
    }

    private fun hideBottomBar() {
        _bottomBarVisibleState.value = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(
                lightScrim = android.graphics.Color.TRANSPARENT,
                darkScrim = android.graphics.Color.TRANSPARENT
            ),
            navigationBarStyle = SystemBarStyle.auto(
                lightScrim = android.graphics.Color.TRANSPARENT,
                darkScrim = android.graphics.Color.TRANSPARENT
            )
        )
        super.onCreate(savedInstanceState)
        setContent {
            val mainViewModel = hiltViewModel<MainViewModel>(viewModelStoreOwner = this)
            LaunchedEffect(Unit) {
                mainViewModel.preLoad()
            }

            val isPreLoaded by mainViewModel.preLoadedState
            splashScreen.setKeepOnScreenCondition { !isPreLoaded }

            val userSettings by mainViewModel.settingsState
            val appState = rememberAppState()
            val appDictionary = AppDictionary(appContext = this)
            LoveMoodMobileTheme {
                WithShimmerTheme {
                    CompositionLocalProvider(
                        LocalMainActivity provides this,
                        LocalAppState provides appState,
                        LocalUserSettings provides userSettings,
                        LocalDateTimePattern provides dateTimePattern,
                        LocalDictionary provides appDictionary
                    ) {
                        MainScaffold(
                            modifier = Modifier
                                .fillMaxSize()
                                .imePadding(),
                            userSettings = userSettings
                        )
                    }
                }
            }
        }
    }

    @Composable
    private fun MainScaffold(
        modifier: Modifier = Modifier,
        userSettings: UserSettings?
    ) {
        val appState = LocalAppState.current
        val currentDestination by appState.currentDestinationState
        NavigationListener(currentDestination)

        val topBarContent = currentDestination?.screen?.topBarContentState
        val bottomBarScreens = remember { AppDestinations.Main }
        val currentBottomBarItem = remember(currentDestination) {
            bottomBarScreens.firstOrNull { item -> item == currentDestination }
        }
        AppScaffold(
            modifier = modifier,
            snackbarHostState = appState.snackbarHostState,
            topBar = topBarContent?.value ?: {},
            isBottomBarVisible = _bottomBarVisibleState.value,
            selectedBottomBarItem = currentBottomBarItem,
            bottomBarItems = bottomBarScreens,
            onBottomBarItemClick = { item ->
                val destination = item as AppDestination
                if (currentDestination != destination) {
                    appState.navigate(
                        destination = destination,
                        clearBackStack = true
                    )
                }
            }
        ) { childModifier ->
            if (userSettings != null) {
                val appDestinations = remember { AppDestinations.All }
                val startDestination = remember(userSettings) {
                    if (userSettings.userProfileId != null) {
                        AppDestinations.Home
                    } else {
                        AppDestinations.Forms.UserProfile
                    }
                }
                AppNavHost(
                    modifier = childModifier,
                    screensModifier = Modifier.fillMaxSize(),
                    navHostController = appState.navHostController,
                    startDestination = startDestination,
                    destinationsList = appDestinations
                )
            }
        }
    }

    @Composable
    private fun NavigationListener(
        currentDestination: AppDestination?
    ) {
        LaunchedEffect(currentDestination) {
            when (currentDestination) {
                is AppMainDestination -> expandBottomBar()
                else -> hideBottomBar()
            }
        }
    }
}