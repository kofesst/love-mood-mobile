package me.kofesst.lovemood.features.validation.operations

abstract class ValidationError

fun interface ValidateOperation<T : Any> {
    fun validate(value: T): ValidationError?
}

fun <T : Any> consumeOperations(
    vararg operations: ValidateOperation<T>
) = ValidateOperation<T> { value ->
    val errors = operations.mapNotNull { it.validate(value) }
    return@ValidateOperation errors.firstOrNull()
}

operator fun <T : Any> ValidateOperation<T>.plus(
    other: ValidateOperation<T>
) = ValidateOperation<T> { value ->
    val firstResult = this.validate(value)
    val secondResult = other.validate(value)
    return@ValidateOperation firstResult ?: secondResult
}

class CastError : ValidationError()

fun interface CastOperation<TInput : Any, TOutput : Any> {
    fun cast(input: TInput): Pair<TOutput?, ValidationError?>
}

fun <TInput : Any, TOutput : Any> consumeOperations(
    castOperation: CastOperation<TInput, TOutput>,
    vararg operations: ValidateOperation<TOutput>
) = ValidateOperation<TInput> { inputValue ->
    val (outputValue, castError) = castOperation.cast(inputValue)
    if (outputValue == null) return@ValidateOperation castError

    val errors = operations.mapNotNull { it.validate(outputValue) }
    return@ValidateOperation errors.firstOrNull()
}