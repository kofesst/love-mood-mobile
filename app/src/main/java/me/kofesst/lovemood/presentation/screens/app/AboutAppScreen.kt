package me.kofesst.lovemood.presentation.screens.app

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import kotlinx.coroutines.launch
import me.kofesst.android.lovemood.navigation.AppScreen
import me.kofesst.lovemood.R
import me.kofesst.lovemood.app.AppDestinations
import me.kofesst.lovemood.app.LocalAppState
import me.kofesst.lovemood.app.dictionary
import me.kofesst.lovemood.core.ui.components.cards.BaseCard
import me.kofesst.lovemood.core.ui.components.scaffold.SmallAppTopBar
import me.kofesst.lovemood.widgets.RelationshipWidget

object AboutAppScreen : AppScreen() {
    @Composable
    override fun ScreenContent(
        modifier: Modifier,
        navBackStackEntry: NavBackStackEntry
    ) {
        val appState = LocalAppState.current
        LaunchedEffect(Unit) {
            updateTopBar {
                SmallAppTopBar(
                    modifier = Modifier.fillMaxWidth(),
                    mainContent = {
                        Text(
                            text = dictionary.screens.app.aboutAppTitle.string(),
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                )
            }
        }

        LazyColumn(
            modifier = modifier.fillMaxSize(),
            contentPadding = PaddingValues(all = 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item(key = "settings_card") {
                SettingsCard { }
            }
            item(key = "todos_card") {
                TodosCard { appState.navigate(AppDestinations.App.Todos) }
            }
            item(key = "widget_cart") {
                val context = LocalContext.current
                WidgetCard {
                    appState.coroutineScope.launch {
                        RelationshipWidget.pinNewWidget(context)
                    }
                }
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
            backgroundImagePainter = painterResource(R.drawable.ic_todo),
            label = dictionary.screens.app.appTodosCardTitle.string(),
            onClick = onClick
        ) {
            Text(
                text = dictionary.screens.app.appTodosCardText.string()
            )
        }
    }

    @Composable
    private fun WidgetCard(
        modifier: Modifier = Modifier,
        onClick: () -> Unit
    ) {
        BaseCard(
            modifier = modifier,
            backgroundImagePainter = painterResource(R.drawable.ic_widget),
            label = dictionary.screens.app.appWidgetCardTitle.string(),
            onClick = onClick
        ) {
            Text(
                text = dictionary.screens.app.appWidgetCardText.string()
            )
        }
    }
}