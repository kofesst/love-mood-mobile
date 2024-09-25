package me.kofesst.lovemood.presentation.forms.memory

import me.kofesst.lovemood.core.models.PhotoMemory
import me.kofesst.lovemood.features.validation.operations.DateValidation
import me.kofesst.lovemood.features.validation.operations.RequiredFieldError
import me.kofesst.lovemood.features.validation.operations.ValidateOperation
import me.kofesst.lovemood.features.validation.operations.consumeOperations
import me.kofesst.lovemood.localization.AppLocalization
import me.kofesst.lovemood.presentation.forms.FormValidator
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MemoryFormValidator @Inject constructor(
    private val localization: AppLocalization
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
                DateValidation.range(
                    minDate = MIN_ASSOCIATED_DATE,
                    maxDate = MAX_ASSOCIATED_DATE,
                )
            ).validate(form.associatedDate)
        } else null
        return form.copy(
            photoContentError = photoContentError?.let {
                localization.errors.fromValidationError(it)
            },
            associatedDateError = associatedDateError?.let {
                localization.errors.fromValidationError(it)
            }
        )
    }
}