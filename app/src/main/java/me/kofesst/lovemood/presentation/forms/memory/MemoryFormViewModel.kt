package me.kofesst.lovemood.presentation.forms.memory

import dagger.hilt.android.lifecycle.HiltViewModel
import me.kofesst.lovemood.core.models.PhotoMemory
import me.kofesst.lovemood.core.usecases.AppUseCases
import me.kofesst.lovemood.features.date.DateTimePattern
import me.kofesst.lovemood.features.validation.operations.DateValidation
import me.kofesst.lovemood.features.validation.operations.RequiredFieldError
import me.kofesst.lovemood.features.validation.operations.ValidateOperation
import me.kofesst.lovemood.features.validation.operations.consumeOperations
import me.kofesst.lovemood.presentation.forms.BaseFormViewModel
import me.kofesst.lovemood.presentation.forms.FormMethod
import me.kofesst.lovemood.ui.text.dictionary.AppDictionary
import me.kofesst.lovemood.ui.text.dictionary.textHolder
import javax.inject.Inject

/**
 * ViewModel формы воспоминания.
 */
@HiltViewModel
class MemoryFormViewModel @Inject constructor(
    private val useCases: AppUseCases,
    private val dateTimePattern: DateTimePattern,
    private val dictionary: AppDictionary
) : BaseFormViewModel<PhotoMemory, MemoryFormState, MemoryFormAction>(
    initialFormState = MemoryFormState(dateTimePattern),
    submitAction = MemoryFormAction.SubmitClicked
) {
    override suspend fun loadEditingModel(modelId: Int): PhotoMemory? {
        return useCases.memories.readById(modelId)
    }

    override suspend fun workWithModel(model: PhotoMemory, method: FormMethod): PhotoMemory {
        return when (method) {
            FormMethod.CreatingNewModel -> {
                val newMemoryId = useCases.memories.create(model)
                model.copy(id = newMemoryId)
            }

            FormMethod.EditingOldModel -> {
                useCases.memories.update(model)
                model
            }
        }
    }

    override fun validateForm(form: MemoryFormState): MemoryFormState {
        val photoContentError = ValidateOperation<ByteArray> { content ->
            if (content.isEmpty()) RequiredFieldError()
            else null
        }.validate(form.photoContent)
        val associatedDateError = if (form.isMemoryAssociatedWithDate) {
            consumeOperations(
                DateValidation.cast(dateTimePattern),
                DateValidation.range(
                    minDate = MemoryFormState.MIN_ASSOCIATED_DATE,
                    maxDate = MemoryFormState.MAX_ASSOCIATED_DATE,
                )
            ).validate(form.associatedDate)
        } else null
        return form.copy(
            photoContentError = photoContentError?.textHolder(dictionary.errors),
            associatedDateError = associatedDateError?.textHolder(dictionary.errors)
        )
    }
}