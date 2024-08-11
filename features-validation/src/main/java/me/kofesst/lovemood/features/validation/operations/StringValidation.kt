package me.kofesst.lovemood.features.validation.operations

class RequiredFieldError : ValidationError()
class ShortValueError : ValidationError()
class LongValueError : ValidationError()
class InvalidMaskError : ValidationError()

object StringValidation {
    val required = ValidateOperation<String> { value ->
        if (value.isBlank()) RequiredFieldError()
        else null
    }

    fun length(lengthRange: IntRange) = ValidateOperation<String> { value ->
        when {
            value.length < lengthRange.first -> ShortValueError()
            value.length > lengthRange.last -> LongValueError()
            else -> null
        }
    }

    fun mask(maskRegex: Regex) = ValidateOperation<String> { value ->
        if (!maskRegex.matches(value)) InvalidMaskError()
        else null
    }
}