package me.kofesst.android.lovemood.navigation

import android.os.Bundle
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.navArgument

/**
 * Класс аргумента любого экрана.
 *
 * [ValueType] - тип значения, который возвращает аргумент.
 */
abstract class DestinationArgument<ValueType : Any> {
    /**
     * Имя аргумента.
     */
    abstract val name: String

    /**
     * Тип аргумента.
     */
    abstract val navType: NavType<*>

    /**
     * Значение аргумента по умолчанию.
     */
    open val defaultValue: ValueType? = null

    /**
     * Лямбда-функция, возвращающая значение аргумента
     * из набора аргументов [Bundle].
     */
    protected abstract val valueFromBundleProducer: (Bundle) -> ValueType?

    /**
     * Получает значение аргумента из стека навигации.
     *
     * Если аргумент не был передан в пути экрана,
     * функция возвращает [defaultValue].
     *
     * Значение аргумента может равняться null.
     */
    fun getFromBundleNullable(backStackEntry: NavBackStackEntry): ValueType? {
        val argumentsBundle = backStackEntry.arguments ?: return defaultValue
        val valueFromProducer = valueFromBundleProducer(argumentsBundle)
        return valueFromProducer ?: defaultValue
    }

    /**
     * Получает значение аргумента из стека навигации.
     *
     * Если аргумент не был передан в пути экрана,
     * функция возвращает [defaultValue], предварительно сообщая,
     * что его значение не может равняться null.
     *
     * Если значение аргумента не перадано в пути экрана, а
     * [defaultValue] равен null, функция вызовет ошибку [NullPointerException].
     */
    fun getFromBundle(backStackEntry: NavBackStackEntry): ValueType {
        return getFromBundleNullable(backStackEntry)!!
    }

    /**
     * Конвертирует аргумент в аргумент навигации [NamedNavArgument]
     * библиотеки [androidx.navigation].
     */
    fun asComposeArgument(): NamedNavArgument {
        return navArgument(name) {
            this.type = this@DestinationArgument.navType
            this.defaultValue = this@DestinationArgument.defaultValue
        }
    }
}

/**
 * Реализация аргумента экрана со значением типа [Int].
 *
 * [defaultValue] должен быть обязательно передан в конструкторе
 * и не может равняться null, так как это вызовет ошибку при реализации
 * системы навигации на основе библиотеки [androidx.navigation].
 */
class IntArgument(
    override val name: String,
    override val defaultValue: Int
) : DestinationArgument<Int>() {
    override val navType: NavType<*> = NavType.IntType
    override val valueFromBundleProducer: (Bundle) -> Int? = { it.getInt(name) }
}

/**
 * Конвертирует список аргументов экрана в список
 * аргументов навигации [NamedNavArgument] с помощью функции
 * [DestinationArgument.asComposeArgument].
 */
fun List<DestinationArgument<*>>.asComposeArguments(): List<NamedNavArgument> {
    return this.map { it.asComposeArgument() }
}