package me.kofesst.lovemood.core.ui.components.scaffold

import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AppScaffold(
    modifier: Modifier = Modifier,
    selectedBottomBarItem: BottomBarItem?,
    bottomBarItems: List<BottomBarItem>,
    onBottomBarItemClick: (BottomBarItem) -> Unit,
    snackbarHostState: SnackbarHostState,
    content: @Composable (Modifier) -> Unit
) {
    Scaffold(
        modifier = modifier,
        bottomBar = {
            AppBottomBar(
                modifier = Modifier.fillMaxWidth(),
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
                .fillMaxSize()
                .consumeWindowInsets(innerPadding)
        )
    }
}