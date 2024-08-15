package me.kofesst.lovemood.presentation.screens.app

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import me.kofesst.lovemood.R
import me.kofesst.lovemood.core.ui.components.cards.BaseCard
import me.kofesst.lovemood.core.ui.components.cards.BaseCardDefaults
import me.kofesst.lovemood.core.ui.utils.mergeWithStatusBar
import me.kofesst.lovemood.presentation.app.LocalAppState
import me.kofesst.lovemood.presentation.app.dictionary
import me.kofesst.lovemood.presentation.navigation.AppNavigation

@Composable
fun AboutAppScreen(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
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
    BaseCard(
        modifier = modifier,
        backgroundImagePainter = painterResource(R.drawable.ic_gear),
        colors = BaseCardDefaults.colors(
            backgroundImageTint = Color(0xFFFECCC0)
        ),
        label = dictionary.screens.app.appSettingsCardTitle.string(),
        onClick = onClick
    ) {
        Text(
            text = dictionary.screens.app.appSettingsCardText.string()
        )
    }
}

@Composable
private fun TodosCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    BaseCard(
        modifier = modifier,
        backgroundImagePainter = painterResource(R.drawable.ic_todo_note),
        colors = BaseCardDefaults.colors(
            backgroundImageTint = Color(0xFFC9FFEA)
        ),
        label = dictionary.screens.app.appTodosCardTitle.string(),
        onClick = onClick
    ) {
        Text(
            text = dictionary.screens.app.appTodosCardText.string()
        )
    }
}

@Composable
private fun VersionHistoryCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    BaseCard(
        modifier = modifier,
        backgroundImagePainter = painterResource(R.drawable.ic_history),
        colors = BaseCardDefaults.colors(
            backgroundImageTint = Color(0xFFDAA1FF)
        ),
        label = dictionary.screens.app.appVersionHistoryCardTitle.string(),
        onClick = onClick
    ) {
        Text(
            text = dictionary.screens.app.appVersionHistoryCardText.string()
        )
    }
}