package me.kofesst.android.lovemood.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import me.kofesst.lovemood.core.text.AppTextHolder
import me.kofesst.lovemood.core.ui.components.scaffold.BottomBarItem

/**
 * Главный пункт назначения, использующийся в нижней навигационной
 * панели приложения, реализуя интерфейс [BottomBarItem].
 */
abstract class AppMainDestination(
    baseRoute: String
) : AppDestination(baseRoute), BottomBarItem {
    abstract override val icon: ImageVector
    abstract override val titleProducer: @Composable () -> AppTextHolder
}