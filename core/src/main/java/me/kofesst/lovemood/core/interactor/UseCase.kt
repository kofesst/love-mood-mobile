package me.kofesst.lovemood.core.interactor

class UseCaseExecutedNullValue : NullPointerException()

/**
 * Параметры юзкейса.
 */
interface UseCaseParams {
    /**
     * Параметры юзкейса с одним значением [value] типа [T].
     */
    class Single<T : Any>(val value: T) : UseCaseParams
}

/**
 * Базовое представление юзкейса.
 *
 * [T] - тип возвращаемого значения.
 */
abstract class UseCase<T : Any> {
    /**
     * Может ли возвращаемое значение быть null.
     */
    abstract val canResultBeNullable: Boolean

    /**
     * Напрямую выполняет операцию.
     */
    protected abstract suspend fun execute(): T?

    /**
     * Выполняет операцию.
     *
     * Возвращает результат выполнения [Result] с типом [T].
     *
     * Если `UseCase.canResultBeNullable = true`, а метод [execute] вернул null,
     * результатом выполнения будет [Result.Failure].
     */
    suspend operator fun invoke(): Result<T> {
        try {
            val value = execute()
            if (value == null && !canResultBeNullable) throw UseCaseExecutedNullValue()
            return Result.success(value!!)
        } catch (exception: Exception) {
            return Result.failure(exception)
        }
    }
}

/**
 * Базовое представление юзкейса.
 *
 * [T] - тип возвращаемого значения.
 *
 * [Params] - параметры вызова.
 */
abstract class ParameterizedUseCase<T : Any, Params : UseCaseParams> {
    /**
     * Может ли возвращаемое значение быть null.
     */
    abstract val canResultBeNullable: Boolean

    /**
     * Напрямую выполняет операцию с параметрами вызова [params].
     */
    protected abstract suspend fun execute(params: Params): T?

    /**
     * Выполняет операцию с параметрами вызова [params].
     *
     * Возвращает результат выполнения [Result] с типом [T].
     *
     * Если `UseCase.canResultBeNullable = true`, а метод [execute] вернул null,
     * результатом выполнения будет [Result.Failure].
     */
    suspend operator fun invoke(params: Params): Result<T> {
        try {
            val value = execute(params)
            if (value == null && !canResultBeNullable) throw UseCaseExecutedNullValue()
            return Result.success(value!!)
        } catch (exception: Exception) {
            return Result.failure(exception)
        }
    }
}