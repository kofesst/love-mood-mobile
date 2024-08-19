package me.kofesst.lovemood.core.ui.components.scaffold

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.animateColor
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
 * [isVisible] - отображается ли панель.
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
    isVisible: Boolean,
    selected: BottomBarItem?,
    items: List<BottomBarItem>,
    iconSize: Dp = AppBottomBarDefaults.iconSize,
    titleStyle: TextStyle = AppBottomBarDefaults.titleStyle,
    layout: AppBottomBarLayout = AppBottomBarDefaults.layout(),
    colors: AppBottomBarColors = AppBottomBarDefaults.colors(),
    shapes: AppBottomBarShapes = AppBottomBarDefaults.shapes(),
    onItemClick: (BottomBarItem) -> Unit
) {
    AnimatedVisibility(
        modifier = modifier,
        visible = isVisible,
        enter = getEnterBarExpandAnim(),
        exit = getExitBarExpandAnim()
    ) {
        AppBottomBarContent(
            modifier = Modifier.fillMaxWidth(),
            selected = selected,
            items = items,
            iconSize = iconSize,
            titleStyle = titleStyle,
            layout = layout,
            colors = colors,
            shapes = shapes,
            onItemClick = onItemClick
        )
    }
}

private const val BAR_EXPAND_ANIM_DURATION = 200

private fun <T : Any> getBarExpandTransitionSpec(): FiniteAnimationSpec<T> {
    return tween(durationMillis = BAR_EXPAND_ANIM_DURATION)
}

private fun getEnterBarExpandAnim(): EnterTransition {
    return slideInVertically(getBarExpandTransitionSpec()) { it * 2 }
}

private fun getExitBarExpandAnim(): ExitTransition {
    return slideOutVertically(getBarExpandTransitionSpec()) { it * 2 }
}

@Composable
fun AppBottomBarContent(
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
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = colors.container,
        shape = shapes.container,
        tonalElevation = colors.tonalElevation,
        shadowElevation = colors.shadowElevation
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(layout.containerPadding),
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
    val selectedState = remember { MutableTransitionState(initialState = isSelected) }
    selectedState.targetState = isSelected

    val transition = updateTransition(selectedState, label = "bbi_expanded_transition")
    val containerColor by transition.animateColor(
        transitionSpec = { getItemExpandAnimSpec() },
        label = "bbi_container_color"
    ) { isExpanded ->
        if (isExpanded) colors.selectedItemContainer else colors.itemContainer
    }
    val iconColor by transition.animateColor(
        transitionSpec = { getItemExpandAnimSpec() },
        label = "bbi_icon_color"
    ) { isExpanded ->
        if (isExpanded) colors.selectedIcon else colors.icon
    }
    AppBottomBarItemContent(
        modifier = modifier,
        item = item,
        selectedState = selectedState,
        containerColor = containerColor,
        iconColor = iconColor,
        iconSize = iconSize,
        titleStyle = titleStyle,
        layout = layout,
        colors = colors,
        shapes = shapes,
        onClick = onClick
    )
}

@Composable
private fun AppBottomBarItemContent(
    modifier: Modifier = Modifier,
    item: BottomBarItem,
    selectedState: MutableTransitionState<Boolean>,
    containerColor: Color,
    iconColor: Color,
    iconSize: Dp = AppBottomBarDefaults.iconSize,
    titleStyle: TextStyle = AppBottomBarDefaults.titleStyle,
    layout: AppBottomBarLayout = AppBottomBarDefaults.layout(),
    colors: AppBottomBarColors = AppBottomBarDefaults.colors(),
    shapes: AppBottomBarShapes = AppBottomBarDefaults.shapes(),
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier.animateContentSize(),
        shape = shapes.item,
        color = containerColor,
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .padding(layout.itemPadding)
                .animateContentSize(),
            horizontalArrangement = layout.itemHorizontalArrangement,
            verticalAlignment = layout.itemVerticalAlignment
        ) {
            Icon(
                modifier = Modifier.size(iconSize),
                imageVector = item.icon,
                contentDescription = null,
                tint = iconColor
            )
            AnimatedVisibility(
                visibleState = selectedState,
                enter = getEnterTextExpandTransition(),
                exit = getExitTextExpandTransition()
            ) {
                Text(
                    text = item.title.string(),
                    style = titleStyle,
                    color = colors.selectedText
                )
            }
        }
    }
}

private const val ITEM_EXPAND_ANIM_DURATION = 200

private fun <T : Any> getItemExpandAnimSpec(): FiniteAnimationSpec<T> {
    return tween(durationMillis = ITEM_EXPAND_ANIM_DURATION)
}

private fun getEnterTextExpandTransition(): EnterTransition {
    return fadeIn(getItemExpandAnimSpec()) + slideInHorizontally(getItemExpandAnimSpec()) { -it }
}

private fun getExitTextExpandTransition(): ExitTransition {
    return fadeOut(getItemExpandAnimSpec()) + slideOutHorizontally(animationSpec = getItemExpandAnimSpec()) { -it }
}