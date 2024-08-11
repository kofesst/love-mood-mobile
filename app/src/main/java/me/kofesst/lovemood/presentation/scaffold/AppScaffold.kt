package me.kofesst.lovemood.presentation.scaffold

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import me.kofesst.lovemood.presentation.app.LocalAppState
import me.kofesst.lovemood.presentation.app.LocalUserSettings

@Composable
fun AppScaffold(
    modifier: Modifier = Modifier,
    content: @Composable (Modifier) -> Unit
) {
    val appState = LocalAppState.current
    Scaffold(
        modifier = modifier,
        snackbarHost = {
            SnackbarHost(
                modifier = Modifier.fillMaxWidth(),
                hostState = appState.snackbarHostState
            )
        }
    ) { innerPadding ->
        val userSettings = LocalUserSettings.current
        if (userSettings != null) {
            content(
                Modifier
                    .fillMaxSize()
                    .consumeWindowInsets(innerPadding)
            )
        } else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(64.dp)
                )
            }
        }
    }
}