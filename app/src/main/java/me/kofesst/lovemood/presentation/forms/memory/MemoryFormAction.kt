package me.kofesst.lovemood.presentation.forms.memory

import me.kofesst.lovemood.core.models.PhotoMemory
import me.kofesst.lovemood.presentation.forms.FormAction

sealed class MemoryFormAction : FormAction<PhotoMemory, MemoryFormState>() {
    @Suppress("ArrayInDataClass")
    data class PhotoContentChanged(val value: ByteArray) : MemoryFormAction() {
        override fun applyToForm(currentForm: MemoryFormState): MemoryFormState {
            return currentForm.copy(photoContent = value)
        }
    }

    data class IsAssociatedWithDateSwitched(val value: Boolean) : MemoryFormAction() {
        override fun applyToForm(currentForm: MemoryFormState): MemoryFormState {
            return currentForm.copy(isMemoryAssociatedWithDate = value)
        }
    }

    data class AssociatedDateChanged(val value: String) : MemoryFormAction() {
        override fun applyToForm(currentForm: MemoryFormState): MemoryFormState {
            return currentForm.copy(associatedDate = value)
        }
    }

    data object SubmitClicked : MemoryFormAction()
}