package me.kofesst.lovemood.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import me.kofesst.lovemood.core.ui.components.scaffold.AppScaffold
import me.kofesst.lovemood.features.date.DateTimePattern
import me.kofesst.lovemood.presentation.app.LocalAppState
import me.kofesst.lovemood.presentation.app.LocalDateTimePattern
import me.kofesst.lovemood.presentation.app.LocalDictionary
import me.kofesst.lovemood.presentation.app.LocalMainActivity
import me.kofesst.lovemood.presentation.app.LocalUserSettings
import me.kofesst.lovemood.presentation.app.rememberAppState
import me.kofesst.lovemood.presentation.navigation.AppNavHost
import me.kofesst.lovemood.ui.text.dictionary.AppDictionary
import me.kofesst.lovemood.ui.theme.LoveMoodMobileTheme
import me.kofesst.lovemood.ui.theme.WithShimmerTheme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var dateTimePattern: DateTimePattern

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
                        AppScaffold(
                            modifier = Modifier
                                .fillMaxSize()
                                .imePadding()
                        ) { modifier ->
                            AppNavHost(
                                modifier = modifier,
                                baseModifier = Modifier.fillMaxSize(),
                                userHasProfile = userSettings?.userProfileId != null
                            )
                        }
                    }
                }
            }
        }
    }
}