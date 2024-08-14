package me.kofesst.lovemood.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry

/**
 * Базовое представление экрана.
 */
abstract class AppScreen(baseRoute: String) {
    /**
     * Путь навигации, созданный из базового пути, переданного в
     * конструкторе класса и списка аргументов [arguments].
     *
     * Например:
     *
     * ```
     *
     * baseRoute = "profile/form"
     *
     * arguments = [
     *     Arg(name = "id", defaultValue = -1),
     *     Arg(name = "continue_route", defaultValue = null)
     * ]
     *
     * AppScreen(baseRoute).route = "profile/form/{continue_route}?id={id}"
     *
     * ```
     *
     * Возвращает строку - шаблон пути навигации к экрану.
     */
    val route: String = buildString {
        append(baseRoute)

        val nonNullArguments = arguments.filter { it.defaultValue == null }
        val nullableArguments = arguments.filter { it.defaultValue != null }

        nonNullArguments.forEach { argument ->
            append("/{${argument.name}}")
        }
        if (nullableArguments.isNotEmpty()) {
            append("?")
            append(
                nullableArguments.joinToString(separator = "&") { argument ->
                    "${argument.name}={${argument.name}}"
                }
            )
        }
    }

    /**
     * Список аргументов экрана.
     */
    open val arguments: List<AppScreenArgument<*>> = emptyList()

    /**
     * Контент экрана.
     *
     * [modifier] - модификатор экрана.
     *
     * [navBackStackEntry] - стек навигации.
     */
    @Composable
    abstract fun ComposableContent(
        modifier: Modifier,
        navBackStackEntry: NavBackStackEntry
    )

    /**
     * Запоминает аргумент экрана, отслеживая [navBackStackEntry].
     *
     * Возвращает значение аргумента или его [AppScreenArgument.defaultValue],
     * если в пути значение аргумента не задано.
     *
     * Может вернуть null, если в пути значение аргумента не задано,
     * а [AppScreenArgument.defaultValue] равно null.
     */
    @Composable
    fun <ValueType : Any> rememberArgumentNullable(
        argument: AppScreenArgument<ValueType>,
        navBackStackEntry: NavBackStackEntry
    ) = remember(navBackStackEntry) {
        argument.getFromBundleNullable(navBackStackEntry.arguments)
    }

    /**
     * Запоминает аргумент экрана, отслеживая [navBackStackEntry].
     *
     * Возвращает значение аргумента или его [AppScreenArgument.defaultValue],
     * если в пути значение аргумента не задано.
     *
     * В том числе отрицает возможность того, что значение по умолчанию может равняться null.
     */
    @Composable
    fun <ValueType : Any> rememberArgument(
        argument: AppScreenArgument<ValueType>,
        navBackStackEntry: NavBackStackEntry
    ) = remember(navBackStackEntry) {
        argument.getFromBundle(navBackStackEntry.arguments)
    }

    /**
     * Создаёт путь навигации к экрану, добавляя значения аргументов [arguments].
     *
     * Например:
     *
     * ```
     *
     * baseRoute = "profile/form"
     *
     * arguments = [
     *     Arg(name = "id", defaultValue = -1),
     *     Arg(name = "continue_route", defaultValue = null)
     * ]
     *
     * AppScreen(baseRoute).route = "profile/form/{continue_route}?id={id}"
     *
     * argumentValues = [
     *     Arg(name = "continue_route", defaultValue = null) to "home"
     * ]
     *
     * AppScreen.withArgs(argumentValues) = "profile/form/home?id=-1"
     *
     * ```
     *
     * [argumentValues] - список значений аргументов.
     *
     * Возвращает строку - путь навигации к экрану.
     */
    fun withArgs(vararg argumentValues: Pair<AppScreenArgument<*>, Any>): String {
        val routeWithValues = argumentValues.fold(route) { acc, (argument, value) ->
            acc.replace("{${argument.name}}", value.toString(), ignoreCase = true)
        }
        val routeWithDefaultValues = arguments.fold(routeWithValues) { acc, argument ->
            acc.replace("{${argument.name}}", argument.defaultValue?.toString() ?: "")
        }
        return routeWithDefaultValues
    }
}