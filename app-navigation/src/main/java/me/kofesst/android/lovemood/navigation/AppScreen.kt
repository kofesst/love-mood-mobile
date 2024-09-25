package me.kofesst.android.lovemood.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry

/**
 * Класс, представляющий любой экран приложения.
 */
abstract class AppScreen() {
    /**
     * Контент экрана.
     *
     * [modifier] - модификатор компонента.
     *
     * [navBackStackEntry] - текущий стек навигации.
     */
    @Composable
    abstract fun ScreenContent(
        modifier: Modifier,
        navBackStackEntry: NavBackStackEntry
    )

    /**
     * Запоминает и возвращает значение аргумента экрана [argument],
     * используя текущий стек навигации [navBackStackEntry].
     *
     * Результат функции может быть равен null.
     */
    @Composable
    protected fun <ValueType : Any> rememberArgumentNullable(
        argument: DestinationArgument<ValueType>,
        navBackStackEntry: NavBackStackEntry
    ): ValueType? = remember(navBackStackEntry) {
        argument.getFromBundleNullable(navBackStackEntry)
    }

    /**
     * Запоминает и возвращает значение аргумента экрана [argument],
     * используя текущий стек навигации [navBackStackEntry].
     *
     * Результат функции никогда не равен null.
     */
    @Composable
    protected fun <ValueType : Any> rememberArgument(
        argument: DestinationArgument<ValueType>,
        navBackStackEntry: NavBackStackEntry
    ): ValueType = remember(navBackStackEntry) {
        argument.getFromBundle(navBackStackEntry)
    }
}