package me.kofesst.android.lovemood.navigation

import android.os.Bundle
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.navArgument
import java.time.LocalDate
import java.time.format.DateTimeFormatter

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
     * Преобразовывает значение аргумента для его
     * последующей записи в набор аргументов.
     */
    open fun prepareForBundle(value: Any?): Any? = value

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
 * Реализация аргумента экрана со значением типа [Boolean].
 *
 * [defaultValue] должен быть обязательно передан в конструкторе
 * и не может равняться null, так как это вызовет ошибку при реализации
 * системы навигации на основе библиотеки [androidx.navigation].
 */
class BoolArgument(
    override val name: String,
    override val defaultValue: Boolean
) : DestinationArgument<Boolean>() {
    override val navType: NavType<*> = NavType.BoolType
    override val valueFromBundleProducer: (Bundle) -> Boolean? = { it.getBoolean(name) }
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
 * Реализация аргумента экрана со значением типа [LocalDate].
 */
class LocalDateArgument(
    override val name: String,
    override val defaultValue: LocalDate = LocalDate.now()
) : DestinationArgument<LocalDate>() {
    override val navType: NavType<*> = LocalDateType
    override val valueFromBundleProducer: (Bundle) -> LocalDate? = { LocalDateType[it, name] }

    override fun prepareForBundle(value: Any?): Any? {
        val date = value as? LocalDate ?: return null
        return LocalDateType.SaverFormatter.format(date)
    }

    private object LocalDateType : NavType<LocalDate>(
        isNullableAllowed = false
    ) {
        const val DATE_SAVER_PATTERN = "dd.MM.yyyy"
        val SaverFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern(DATE_SAVER_PATTERN)

        override fun get(bundle: Bundle, key: String): LocalDate? {
            val valueString = bundle.getString(key) ?: return null
            return this.parseValue(valueString)
        }

        override fun parseValue(value: String): LocalDate {
            return try {
                LocalDate.parse(value, SaverFormatter)
            } catch (_: Exception) {
                LocalDate.now()
            }
        }

        override fun put(bundle: Bundle, key: String, value: LocalDate) {
            val valueString = SaverFormatter.format(value)
            bundle.putString(key, valueString)
        }
    }
}

/**
 * Конвертирует список аргументов экрана в список
 * аргументов навигации [NamedNavArgument] с помощью функции
 * [DestinationArgument.asComposeArgument].
 */
fun List<DestinationArgument<*>>.asComposeArguments(): List<NamedNavArgument> {
    return this.map { it.asComposeArgument() }
}