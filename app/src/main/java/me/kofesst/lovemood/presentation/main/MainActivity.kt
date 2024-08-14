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
import me.kofesst.lovemood.core.models.UserSettings
import me.kofesst.lovemood.core.ui.components.scaffold.AppBottomBarSettings
import me.kofesst.lovemood.core.ui.components.scaffold.AppScaffold
import me.kofesst.lovemood.features.date.DateTimePattern
import me.kofesst.lovemood.presentation.app.LocalAppState
import me.kofesst.lovemood.presentation.app.LocalDateTimePattern
import me.kofesst.lovemood.presentation.app.LocalDictionary
import me.kofesst.lovemood.presentation.app.LocalMainActivity
import me.kofesst.lovemood.presentation.app.LocalUserSettings
import me.kofesst.lovemood.presentation.app.rememberAppState
import me.kofesst.lovemood.presentation.navigation.AppMainScreen
import me.kofesst.lovemood.presentation.navigation.AppNavHost
import me.kofesst.lovemood.presentation.navigation.AppNavigation
import me.kofesst.lovemood.presentation.navigation.AppScreen
import me.kofesst.lovemood.presentation.navigation.BottomBarScreenItem
import me.kofesst.lovemood.ui.text.dictionary.AppDictionary
import me.kofesst.lovemood.ui.theme.LoveMoodMobileTheme
import me.kofesst.lovemood.ui.theme.WithShimmerTheme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var dateTimePattern: DateTimePattern

    private val _bottomBarExpandState = mutableStateOf(false)

    private fun expandBottomBar() {
        _bottomBarExpandState.value = true
    }

    private fun hideBottomBar() {
        _bottomBarExpandState.value = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(
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
        val currentScreen by appState.currentScreenState
        NavigationListener(currentScreen)

        val bottomBarSettings = remember(_bottomBarExpandState.value) {
            AppBottomBarSettings(
                isVisible = _bottomBarExpandState.value
            )
        }
        val bottomBarScreens = remember {
            AppNavigation.getBottomBarScreens(this@MainActivity)
        }
        val currentBottomBarItem = remember(currentScreen) {
            bottomBarScreens.firstOrNull { item -> item.destination == currentScreen }
        }
        AppScaffold(
            modifier = modifier,
            snackbarHostState = appState.snackbarHostState,
            bottomBarSettings = bottomBarSettings,
            selectedBottomBarItem = currentBottomBarItem,
            bottomBarItems = bottomBarScreens,
            onBottomBarItemClick = { item ->
                val screenItem = item as BottomBarScreenItem
                if (currentScreen != screenItem.destination) {
                    appState.navigate(
                        appScreen = screenItem.destination,
                        clearBackStack = true
                    )
                }
            }
        ) { childModifier ->
            if (userSettings != null) {
                AppNavHost(
                    modifier = childModifier,
                    baseModifier = Modifier.fillMaxSize(),
                    userHasProfile = userSettings.userProfileId != null
                )
            }
        }
    }

    @Composable
    private fun NavigationListener(
        currentScreen: AppScreen?
    ) {
        LaunchedEffect(currentScreen) {
            when (currentScreen) {
                is AppMainScreen -> expandBottomBar()
                else -> hideBottomBar()
            }
        }
    }
}