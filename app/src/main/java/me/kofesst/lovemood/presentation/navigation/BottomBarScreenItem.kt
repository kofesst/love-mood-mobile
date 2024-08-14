package me.kofesst.lovemood.presentation.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import me.kofesst.lovemood.core.text.AppTextHolder
import me.kofesst.lovemood.core.ui.components.scaffold.BottomBarItem

class BottomBarScreenItem(
    title: AppTextHolder,
    icon: ImageVector,
    val destination: AppScreen
) : BottomBarItem(title, icon)