package me.kofesst.lovemood.presentation.forms.memory

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import me.kofesst.lovemood.core.interactor.UseCaseParams
import me.kofesst.lovemood.core.interactor.memory.MemoryInteractor
import me.kofesst.lovemood.core.models.PhotoMemory
import me.kofesst.lovemood.features.date.DateTimePattern
import me.kofesst.lovemood.presentation.forms.BaseFormViewModel
import me.kofesst.lovemood.presentation.forms.FormMethod
import javax.inject.Inject

/**
 * ViewModel формы воспоминания.
 */
@HiltViewModel
class MemoryFormViewModel @Inject constructor(
    private val memoryInteractor: MemoryInteractor,
    dateTimePattern: DateTimePattern,
    validator: MemoryFormValidator
) : BaseFormViewModel<PhotoMemory, MemoryFormState, MemoryFormAction>(
    initialFormState = MemoryFormState(dateTimePattern),
    validator = validator,
    submitActionClass = MemoryFormAction.SubmitClicked.javaClass
) {
    /**
     * Устанавливает редактируемую модель воспоминания с ID [memoryId].
     */
    fun setEditingModel(memoryId: Int) {
        viewModelScope.launch {
            val editingModel = memoryInteractor.get(
                params = UseCaseParams.Single.with(memoryId)
            ).getOrNull() ?: return@launch
            manuallyEditForm { currentForm ->
                currentForm.fromModel(editingModel)
            }
            changeFormMethod(FormMethod.EditingOldModel)
            prepareForm()
        }
    }

    override suspend fun workWithModel(model: PhotoMemory, method: FormMethod): PhotoMemory {
        when (method) {
            FormMethod.CreatingNewModel -> {
                return memoryInteractor.create(
                    params = UseCaseParams.Single.with(model)
                ).getOrThrow()
            }

            FormMethod.EditingOldModel -> {
                memoryInteractor.update(
                    params = UseCaseParams.Single.with(model)
                )
                return model
            }
        }
    }
}