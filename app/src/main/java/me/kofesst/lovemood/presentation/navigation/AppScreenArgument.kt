@file:Suppress("unused")

package me.kofesst.lovemood.presentation.navigation

import android.os.Bundle
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

/**
 * Базовое представление аргумента экрана приложения.
 *
 * [ValueType] - тип значения, передаваемого в аргументе.
 */
abstract class AppScreenArgument<ValueType : Any> {
    /**
     * Имя аргумента.
     */
    abstract val name: String

    /**
     * Тип аргумента.
     */
    abstract val type: NavType<*>

    /**
     * Значение аргумента по умолчанию.
     *
     * Изначально равно null.
     */
    open val defaultValue: ValueType? = null

    /**
     * Получает значение аргумента из набора аргументов [argumentsBundle].
     *
     * [argumentsBundle] - набор аргументов, переданный вместе
     * со стэком навигации.
     *
     * Возвращает значение аргумента с типом [ValueType] (может равняться null).
     */
    abstract fun getValueFromBundle(argumentsBundle: Bundle?): ValueType?

    /**
     * Получает значение аргумента из набора аргументов [argumentsBundle].
     *
     * Если значение аргумента не передано, возвращает значение аргумента
     * по умолчанию [defaultValue].
     *
     * Возвращает значение аргумента [ValueType] (может равняться null).
     */
    fun getFromBundleNullable(argumentsBundle: Bundle?): ValueType? {
        if (argumentsBundle == null) return defaultValue
        val value = getValueFromBundle(argumentsBundle) ?: defaultValue
        return value
    }

    /**
     * Получает значение аргумента из набора аргументов [argumentsBundle].
     *
     * Если значение аргумента не передано, возвращает значение аргумента
     * по умолчанию [defaultValue].
     *
     * Возвращает значение аргумента [ValueType] (не может равняться null).
     *
     * Если значение аргумента не передано, а значение аргумента по умолчанию
     * [defaultValue] равно null, вызывает исключение [NullPointerException].
     */
    fun getFromBundle(argumentsBundle: Bundle?): ValueType {
        if (argumentsBundle == null) return defaultValue!!
        val value = getValueFromBundle(argumentsBundle) ?: defaultValue!!
        return value
    }

    /**
     * Конвертирует данное представление аргумента экрана в
     * аргумент навигации [NamedNavArgument] библиотеки [androidx.navigation].
     */
    fun asComposeArgument(): NamedNavArgument = navArgument(name) {
        this.type = this@AppScreenArgument.type
        this.defaultValue = this@AppScreenArgument.defaultValue
    }
}

/**
 * Конвертирует список аргументов экрана в список аргументов
 * навигации [NamedNavArgument] библиотеки [androidx.navigation].
 */
fun List<AppScreenArgument<*>>.asComposeArguments() = map { it.asComposeArgument() }

/**
 * Реализация аргумента экрана, передающего значение типа [Boolean].
 *
 * Значение аргумента по умолчанию не может равняться null,
 * и должно быть обязательно передано в конструкторе класса.
 */
class BooleanArgument(
    override val name: String,
    override val defaultValue: Boolean
) : AppScreenArgument<Boolean>() {
    override val type: NavType<*> = NavType.BoolType

    override fun getValueFromBundle(argumentsBundle: Bundle?): Boolean? {
        return argumentsBundle?.getBoolean(name)
    }
}

/**
 * Реализация аргумента экрана, передающего значение типа [Int].
 *
 * Значение аргумента по умолчанию не может равняться null,
 * и должно быть обязательно передано в конструкторе класса.
 */
class IntArgument(
    override val name: String,
    override val defaultValue: Int
) : AppScreenArgument<Int>() {
    override val type: NavType<*> = NavType.IntType

    override fun getValueFromBundle(argumentsBundle: Bundle?): Int? {
        return argumentsBundle?.getInt(name)
    }
}

/**
 * Реализация аргумента экрана, передающего значение типа [String].
 */
class StringArgument(
    override val name: String,
    override val defaultValue: String? = null
) : AppScreenArgument<String>() {
    override val type: NavType<*> = NavType.StringType

    override fun getValueFromBundle(argumentsBundle: Bundle?): String? {
        return argumentsBundle?.getString(name)
    }
}