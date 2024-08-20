package me.kofesst.lovemood.presentation.forms.memory

import android.content.Context
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import me.kofesst.lovemood.core.models.PhotoMemory
import me.kofesst.lovemood.core.usecases.AppUseCases
import me.kofesst.lovemood.features.date.DateTimePattern
import me.kofesst.lovemood.features.validation.operations.DateValidation
import me.kofesst.lovemood.features.validation.operations.RequiredFieldError
import me.kofesst.lovemood.features.validation.operations.ValidateOperation
import me.kofesst.lovemood.features.validation.operations.consumeOperations
import me.kofesst.lovemood.localization.dictionary.AppDictionary
import me.kofesst.lovemood.presentation.forms.BaseFormViewModel
import me.kofesst.lovemood.presentation.forms.FormMethod
import me.kofesst.lovemood.ui.uiText
import javax.inject.Inject

/**
 * ViewModel формы воспоминания.
 */
@HiltViewModel
class MemoryFormViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val useCases: AppUseCases,
    private val dateTimePattern: DateTimePattern,
    private val dictionary: AppDictionary
) : BaseFormViewModel<PhotoMemory, MemoryFormState, MemoryFormAction>(
    initialFormState = MemoryFormState(dateTimePattern),
    applicationContext = context,
    useCases = useCases,
    dictionary = dictionary,
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
            photoContentError = photoContentError?.uiText(dictionary.errors),
            associatedDateError = associatedDateError?.uiText(dictionary.errors)
        )
    }
}