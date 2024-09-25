package me.kofesst.lovemood.main

import android.graphics.Color
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import me.kofesst.android.lovemood.navigation.AppNavHost
import me.kofesst.lovemood.core.models.UserSession
import me.kofesst.lovemood.core.ui.components.scaffold.AppScaffold
import me.kofesst.lovemood.localization.AppLocalization
import me.kofesst.lovemood.presentation.Destinations
import me.kofesst.lovemood.presentation.LocalAppState
import me.kofesst.lovemood.presentation.LocalLocalization
import me.kofesst.lovemood.presentation.LocalMainViewModelStoreOwner
import me.kofesst.lovemood.presentation.rememberAppState
import me.kofesst.lovemood.ui.theme.LoveMoodMobileTheme
import me.kofesst.lovemood.ui.theme.WithShimmerTheme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var localization: AppLocalization

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(
                lightScrim = Color.TRANSPARENT,
                darkScrim = Color.TRANSPARENT
            ),
            navigationBarStyle = SystemBarStyle.auto(
                lightScrim = Color.TRANSPARENT,
                darkScrim = Color.TRANSPARENT
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

            val userSession by mainViewModel.sessionState
            val appState = rememberAppState()
            LoveMoodMobileTheme {
                WithShimmerTheme {
                    CompositionLocalProvider(
                        LocalMainViewModelStoreOwner provides this,
                        LocalAppState provides appState,
                        LocalLocalization provides localization
                    ) {
                        MainScaffold(
                            modifier = Modifier
                                .fillMaxSize()
                                .imePadding(),
                            userSession = userSession
                        )
                    }
                }
            }
        }
    }

    @Composable
    private fun MainScaffold(
        modifier: Modifier = Modifier,
        userSession: UserSession?
    ) {
        val appState = LocalAppState.current
        AppScaffold(
            modifier = modifier,
            snackbarHostState = appState.snackbarHostState
        ) { childModifier ->
            if (userSession != null) {
                val startDestination = remember(userSession) {
                    Destinations.getInitialDestination(
                        userHasProfile = userSession.profileId != null
                    )
                }
                AppNavHost(
                    modifier = childModifier,
                    screensModifier = Modifier.fillMaxSize(),
                    navHostController = appState.navHostController,
                    startDestination = startDestination,
                    destinationsList = Destinations.getAllDestinations()
                )
            }
        }
    }
}