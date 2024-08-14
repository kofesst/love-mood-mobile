package me.kofesst.lovemood.presentation.screens.app.todos

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBackIos
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import me.kofesst.lovemood.core.ui.utils.mergeWithStatusBar
import me.kofesst.lovemood.core.ui.utils.navigationBarPadding
import me.kofesst.lovemood.presentation.app.LocalAppState
import me.kofesst.lovemood.presentation.app.LocalDictionary
import me.kofesst.lovemood.presentation.screens.app.AppScreenHeader
import me.kofesst.lovemood.ui.text.dictionary.uiText

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AppTodosScreen(
    modifier: Modifier = Modifier
) {
    val dictionary = LocalDictionary.current
    val todoItems = remember(dictionary) { AppTodosRepository(dictionary.todos).all }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        contentPadding = navigationBarPadding()
    ) {
        stickyHeader(key = "screen_top_bar") {
            ScreenTopBar(modifier = Modifier.fillMaxWidth())
        }
        item(key = "screen_header") {
            AppScreenHeader(modifier = Modifier.fillMaxWidth())
        }
        items(todoItems, key = { "todo_item__${it.title}" }) { todoItem ->
            AppTodoItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                item = todoItem
            )
        }
    }
}

@Composable
private fun ScreenTopBar(
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp.mergeWithStatusBar()),
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val appState = LocalAppState.current
            IconButton(onClick = appState::navigateUp) {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowBackIos,
                    contentDescription = null
                )
            }
            Text(
                text = "План разработки",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun AppTodoItem(
    modifier: Modifier = Modifier,
    item: AppTodo
) {
    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.surfaceVariant,
        contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = item.title.string(),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = item.description.string(),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Normal
            )
            Text(
                text = buildAnnotatedString {
                    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("Статус: ")
                    }
                    append(item.status.uiText)
                }
            )
        }
    }
}