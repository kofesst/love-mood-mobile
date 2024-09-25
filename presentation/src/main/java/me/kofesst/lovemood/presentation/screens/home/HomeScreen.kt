package me.kofesst.lovemood.presentation.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import me.kofesst.android.lovemood.navigation.AppScreen
import me.kofesst.lovemood.presentation.LocalMainViewModelStoreOwner
import me.kofesst.lovemood.presentation.WithShimmerEffect

object HomeScreen : AppScreen() {
    @Composable
    override fun ScreenContent(
        modifier: Modifier,
        navBackStackEntry: NavBackStackEntry
    ) {
        val mainOwner = LocalMainViewModelStoreOwner.current
        val viewModel = hiltViewModel<HomeViewModel>(viewModelStoreOwner = mainOwner)
        LaunchedEffect(Unit) {
            viewModel.loadData()
        }
        Content(
            modifier = modifier,
            viewModel = viewModel
        )
    }

    @Composable
    private fun Content(
        modifier: Modifier = Modifier,
        viewModel: HomeViewModel
    ) {
        val profileSnapshot by viewModel.profileLoadingFlow.collectAsState()
        val relationshipSnapshot by viewModel.relationshipLoadingFlow.collectAsState()
        val memoriesSnapshot by viewModel.memoriesLoadingFlow.collectAsState()
        WithShimmerEffect {
            Column(
                modifier = modifier,
                verticalArrangement = Arrangement.spacedBy(space = 20.dp)
            ) {
                HomeScreenHeader(
                    modifier = Modifier
                        .padding(20.dp)
                        .statusBarsPadding()
                        .fillMaxWidth(),
                    profileSnapshot = profileSnapshot,
                    relationshipSnapshot = relationshipSnapshot
                )
            }
        }
    }
}