package me.kofesst.lovemood.presentation.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavBackStackEntry
import me.kofesst.lovemood.core.text.AppTextHolder

abstract class AppMainScreen(baseRoute: String) : AppScreen(baseRoute) {
    abstract val bottomBarIcon: ImageVector
    abstract val bottomBarTitleProducer: (Context) -> AppTextHolder

    @Composable
    abstract override fun ComposableContent(
        modifier: Modifier,
        navBackStackEntry: NavBackStackEntry
    )
}