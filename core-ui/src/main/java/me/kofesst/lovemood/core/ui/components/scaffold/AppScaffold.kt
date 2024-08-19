package me.kofesst.lovemood.core.ui.components.scaffold

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import me.kofesst.lovemood.core.ui.transitions.topBarAnimatedTransition

typealias ComposableContent = @Composable () -> Unit

@Composable
fun AppScaffold(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit = {},
    isBottomBarVisible: Boolean,
    selectedBottomBarItem: BottomBarItem?,
    bottomBarItems: List<BottomBarItem>,
    onBottomBarItemClick: (BottomBarItem) -> Unit,
    snackbarHostState: SnackbarHostState,
    content: @Composable (Modifier) -> Unit
) {
    Scaffold(
        modifier = modifier,
        contentWindowInsets = WindowInsets(0.dp),
        topBar = {
            AnimatedContent(
                targetState = topBar,
                label = "top_bar",
                transitionSpec = { topBarAnimatedTransition }
            ) { topBarContent ->
                topBarContent()
            }
        },
        bottomBar = {
            AppBottomBar(
                modifier = Modifier.fillMaxWidth(),
                isVisible = isBottomBarVisible,
                selected = selectedBottomBarItem,
                items = bottomBarItems,
                onItemClick = onBottomBarItemClick
            )
        },
        snackbarHost = {
            SnackbarHost(
                modifier = Modifier.fillMaxWidth(),
                hostState = snackbarHostState
            )
        }
    ) { innerPadding ->
        content(
            Modifier
                .consumeWindowInsets(innerPadding)
                .padding(innerPadding)
                .fillMaxSize()
                .animateContentSize()
        )
    }
}