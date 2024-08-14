package me.kofesst.lovemood.core.ui.components.scaffold

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import me.kofesst.lovemood.core.text.AppTextHolder

/**
 * Информация элемента нижней панели приложения.
 *
 * [title] - текст элемента.
 *
 * [icon] - иконка элемента.
 */
open class BottomBarItem(
    val title: AppTextHolder,
    val icon: ImageVector
)

/**
 * Нижняя панель приложения.
 *
 * [selected] - выбранный элемент.
 *
 * [items] - элементы панели.
 *
 * [onItemClick] - функция, вызываемая при нажатии
 * на один из элементов панели.
 */
@Composable
fun AppBottomBar(
    modifier: Modifier = Modifier,
    selected: BottomBarItem?,
    items: List<BottomBarItem>,
    iconSize: Dp = AppBottomBarDefaults.iconSize,
    titleStyle: TextStyle = AppBottomBarDefaults.titleStyle,
    layout: AppBottomBarLayout = AppBottomBarDefaults.layout(),
    colors: AppBottomBarColors = AppBottomBarDefaults.colors(),
    shapes: AppBottomBarShapes = AppBottomBarDefaults.shapes(),
    onItemClick: (BottomBarItem) -> Unit
) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            modifier = Modifier.padding(layout.layoutPadding),
            color = colors.container,
            shape = shapes.container,
            tonalElevation = colors.tonalElevation,
            shadowElevation = colors.shadowElevation
        ) {
            Row(
                modifier = Modifier.padding(layout.containerPadding),
                horizontalArrangement = layout.containerHorizontalArrangement,
                verticalAlignment = layout.containerVerticalAlignment
            ) {
                items.forEach { item ->
                    AppBottomBarItem(
                        item = item,
                        isSelected = selected == item,
                        iconSize = iconSize,
                        titleStyle = titleStyle,
                        layout = layout,
                        colors = colors,
                        shapes = shapes,
                        onClick = { onItemClick(item) }
                    )
                }
            }
        }
    }
}

/**
 * Элемент нижней панели приложения.
 *
 * [item] - элемент панели.
 *
 * [isSelected] - является элемент выбранным.
 *
 * [onClick] - функция, вызываемая при нажатии на элемент.
 */
@Composable
fun AppBottomBarItem(
    modifier: Modifier = Modifier,
    item: BottomBarItem,
    isSelected: Boolean,
    iconSize: Dp = AppBottomBarDefaults.iconSize,
    titleStyle: TextStyle = AppBottomBarDefaults.titleStyle,
    layout: AppBottomBarLayout = AppBottomBarDefaults.layout(),
    colors: AppBottomBarColors = AppBottomBarDefaults.colors(),
    shapes: AppBottomBarShapes = AppBottomBarDefaults.shapes(),
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .clip(shapes.item)
            .clickable(onClick = onClick)
    ) {
        Column(
            modifier = Modifier.padding(layout.itemPadding),
            horizontalAlignment = layout.itemHorizontalAlignment,
            verticalArrangement = layout.itemVerticalArrangement
        ) {
            Icon(
                modifier = Modifier.size(iconSize),
                imageVector = item.icon,
                contentDescription = item.title.string(),
                tint = if (isSelected) colors.selectedIcon else colors.icon
            )
            Text(
                text = item.title.string(),
                style = titleStyle.copy(
                    color = if (isSelected) colors.selectedText else colors.text
                )
            )
        }
    }
}