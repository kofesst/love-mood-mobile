package me.kofesst.lovemood.presentation.screens.app

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import me.kofesst.lovemood.core.ui.utils.mergeWithStatusBar
import me.kofesst.lovemood.presentation.app.LocalAppState
import me.kofesst.lovemood.presentation.app.dictionary
import me.kofesst.lovemood.presentation.navigation.AppNavigation

@Composable
fun AboutAppScreen(
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp.mergeWithStatusBar()),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            AppScreenHeader()

            val appState = LocalAppState.current
            SettingsCard {}
            TodosCard { appState.navigate(AppNavigation.AppTodosScreen) }
            VersionHistoryCard {}
        }
    }
}

@Composable
private fun SettingsCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    AppScreenCard(
        modifier = modifier,
        title = dictionary.screens.app.appSettingsCardTitle.string(),
        text = dictionary.screens.app.appSettingsCardText.string(),
        onClick = onClick
    )
}

@Composable
private fun TodosCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    AppScreenCard(
        modifier = modifier,
        title = dictionary.screens.app.appTodosCardTitle.string(),
        text = dictionary.screens.app.appTodosCardText.string(),
        onClick = onClick
    )
}

@Composable
private fun VersionHistoryCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    AppScreenCard(
        modifier = modifier,
        title = dictionary.screens.app.appVersionHistoryCardTitle.string(),
        text = dictionary.screens.app.appVersionHistoryCardText.string(),
        onClick = onClick
    )
}