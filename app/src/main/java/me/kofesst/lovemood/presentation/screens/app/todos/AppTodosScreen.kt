package me.kofesst.lovemood.presentation.screens.app.todos

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import me.kofesst.android.lovemood.navigation.AppScreen
import me.kofesst.lovemood.app.LocalAppState
import me.kofesst.lovemood.app.LocalDictionary
import me.kofesst.lovemood.app.dictionary
import me.kofesst.lovemood.core.ui.components.cards.BaseCard
import me.kofesst.lovemood.core.ui.components.scaffold.NavigateUpIconButton
import me.kofesst.lovemood.core.ui.components.scaffold.SmallAppTopBar
import me.kofesst.lovemood.core.ui.utils.alsoNavBar
import me.kofesst.lovemood.presentation.screens.app.AppScreenHeader
import me.kofesst.lovemood.ui.text.dictionary.uiText

object AppTodosScreen : AppScreen() {
    @Composable
    override fun ScreenContent(
        modifier: Modifier,
        navBackStackEntry: NavBackStackEntry
    ) {
        val dictionary = LocalDictionary.current
        val appState = LocalAppState.current
        LaunchedEffect(Unit) {
            updateTopBar {
                SmallAppTopBar(
                    modifier = Modifier.fillMaxWidth(),
                    leftContent = {
                        NavigateUpIconButton { appState.navigateUp() }
                    },
                    mainContent = {
                        Text(
                            text = dictionary.screens.app.appTodosScreenTitle.string(),
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                )
            }
        }

        val todoItems = remember(dictionary) { AppTodosRepository(dictionary.todos).all }
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            contentPadding = PaddingValues(all = 20.dp).alsoNavBar(),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item(key = "screen_header") {
                AppScreenHeader(modifier = Modifier.fillMaxWidth())
            }
            items(todoItems, key = { "todo_item__${it.title}" }) { todoItem ->
                AppTodoItem(
                    modifier = Modifier.fillMaxWidth(),
                    item = todoItem
                )
            }
        }
    }

    @Composable
    private fun AppTodoItem(
        modifier: Modifier = Modifier,
        item: AppTodo
    ) {
        BaseCard(
            modifier = modifier,
            label = item.title.string()
        ) {
            Text(
                text = item.description.string()
            )
            Text(
                text = dictionary.screens.app.todoStatus.string(
                    "%todo_status%" to item.status.uiText,
                    "%app_version%" to ((item.status as? AppTodo.Status.Released)?.appVersion ?: "")
                )
            )
        }
    }
}