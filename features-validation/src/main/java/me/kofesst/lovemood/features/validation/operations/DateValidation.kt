package me.kofesst.lovemood.features.validation.operations

import me.kofesst.lovemood.features.date.DateTimePattern
import java.time.LocalDate
import java.time.format.DateTimeParseException

class InvalidDateError : ValidationError()
class TooEarlyDateError : ValidationError()
class TooLateDateError : ValidationError()

object DateValidation {
    fun cast(pattern: DateTimePattern) = CastOperation<String, LocalDate> { stringValue ->
        try {
            pattern.parseDate(stringValue) to null
        } catch (parseException: DateTimeParseException) {
            null to InvalidDateError()
        }
    }

    fun range(minDate: LocalDate, maxDate: LocalDate) = ValidateOperation<LocalDate> { date ->
        when {
            date < minDate -> TooEarlyDateError()
            date > maxDate -> TooLateDateError()
            else -> null
        }
    }
}