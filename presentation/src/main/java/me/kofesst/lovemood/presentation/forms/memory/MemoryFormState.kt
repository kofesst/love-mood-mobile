package me.kofesst.lovemood.presentation.forms.memory

import me.kofesst.lovemood.core.models.PhotoMemory
import me.kofesst.lovemood.localization.text.TextBuilder
import me.kofesst.lovemood.presentation.forms.FormState
import java.time.LocalDate
import java.time.LocalDateTime

@Suppress("ArrayInDataClass")
data class MemoryFormState(
    val id: Int = 0,
    val photoContent: ByteArray = byteArrayOf(),
    val photoContentError: TextBuilder? = null,
    val isMemoryAssociatedWithDate: Boolean = false,
    val associatedDate: LocalDate = MemoryFormValidator.MAX_ASSOCIATED_DATE,
    val associatedDateError: TextBuilder? = null
) : FormState<PhotoMemory>() {
    override val isValid: Boolean
        get() = photoContentError == null && associatedDateError == null

    override val isFilled: Boolean
        get() = photoContent.isNotEmpty()

    override fun asModel(): PhotoMemory {
        return PhotoMemory(
            id = id,
            photoContent = photoContent,
            addedAt = LocalDateTime.now(),
            associatedDate = if (!isMemoryAssociatedWithDate) null else associatedDate
        )
    }

    override fun fromModel(model: PhotoMemory): MemoryFormState {
        val isAssociatedWithDate = model.associatedDate != null
        return copy(
            id = model.id,
            photoContent = model.photoContent,
            associatedDate = if (!isAssociatedWithDate) MemoryFormValidator.MAX_ASSOCIATED_DATE
            else model.associatedDate!!
        )
    }
}
