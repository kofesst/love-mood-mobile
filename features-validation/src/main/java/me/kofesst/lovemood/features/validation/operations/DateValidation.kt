package me.kofesst.lovemood.features.validation.operations

import java.time.LocalDate

class TooEarlyDateError : ValidationError()
class TooLateDateError : ValidationError()

object DateValidation {
    fun range(minDate: LocalDate, maxDate: LocalDate) = ValidateOperation<LocalDate> { date ->
        when {
            date < minDate -> TooEarlyDateError()
            date > maxDate -> TooLateDateError()
            else -> null
        }
    }
}