package me.kofesst.android.lovemood.navigation

/**
 * Базовый пункт назначения навигации приложения.
 */
abstract class AppDestination(
    /**
     * Базовый путь.
     */
    baseRoute: String,
    val arguments: List<DestinationArgument<*>> = emptyList()
) {
    /**
     * Экран пункта назначения.
     */
    abstract val screen: AppScreen

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
    fun withArgs(vararg argumentValues: Pair<DestinationArgument<*>, Any>): String {
        val routeWithValues = argumentValues.fold(route) { acc, (argument, value) ->
            acc.replace("{${argument.name}}", value.toString(), ignoreCase = true)
        }
        val routeWithDefaultValues = arguments.fold(routeWithValues) { acc, argument ->
            acc.replace("{${argument.name}}", argument.defaultValue?.toString() ?: "")
        }
        return routeWithDefaultValues
    }
}