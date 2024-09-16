package me.kofesst.lovemood.presentation.forms.memory

import me.kofesst.lovemood.core.models.PhotoMemory
import me.kofesst.lovemood.features.date.DateTimePattern
import me.kofesst.lovemood.features.validation.operations.DateValidation
import me.kofesst.lovemood.features.validation.operations.RequiredFieldError
import me.kofesst.lovemood.features.validation.operations.ValidateOperation
import me.kofesst.lovemood.features.validation.operations.consumeOperations
import me.kofesst.lovemood.localization.dictionary.AppDictionary
import me.kofesst.lovemood.presentation.forms.FormValidator
import me.kofesst.lovemood.ui.uiText
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MemoryFormValidator @Inject constructor(
    private val dateTimePattern: DateTimePattern,
    private val dictionary: AppDictionary
) : FormValidator<PhotoMemory, MemoryFormState> {
    companion object {
        val MIN_ASSOCIATED_DATE: LocalDate = LocalDate.MIN
        val MAX_ASSOCIATED_DATE: LocalDate = LocalDate.now()
    }

    override fun validate(form: MemoryFormState): MemoryFormState {
        val photoContentError = ValidateOperation<ByteArray> { content ->
            if (content.isEmpty()) RequiredFieldError()
            else null
        }.validate(form.photoContent)
        val associatedDateError = if (form.isMemoryAssociatedWithDate) {
            consumeOperations(
                DateValidation.cast(dateTimePattern),
                DateValidation.range(
                    minDate = MIN_ASSOCIATED_DATE,
                    maxDate = MAX_ASSOCIATED_DATE,
                )
            ).validate(form.associatedDate)
        } else null
        return form.copy(
            photoContentError = photoContentError?.uiText(dictionary.errors),
            associatedDateError = associatedDateError?.uiText(dictionary.errors)
        )
    }
}