package me.kofesst.lovemood.presentation.forms.memory

import me.kofesst.lovemood.core.models.PhotoMemory
import me.kofesst.lovemood.core.text.AppTextHolder
import me.kofesst.lovemood.features.date.DateTimePattern
import me.kofesst.lovemood.presentation.forms.FormState
import java.time.LocalDate
import java.time.LocalDateTime

@Suppress("ArrayInDataClass")
data class MemoryFormState(
    private val dateTimePattern: DateTimePattern,
    val id: Int = 0,
    val photoContent: ByteArray = byteArrayOf(),
    val photoContentError: AppTextHolder? = null,
    val isMemoryAssociatedWithDate: Boolean = false,
    val associatedDate: String = "",
    val associatedDateError: AppTextHolder? = null
) : FormState<PhotoMemory>() {
    companion object {
        val MIN_ASSOCIATED_DATE: LocalDate = LocalDate.MIN
        val MAX_ASSOCIATED_DATE: LocalDate = LocalDate.now()
    }

    override val isValid: Boolean
        get() = photoContentError == null && associatedDateError == null

    override val isFilled: Boolean
        get() = photoContent.isNotEmpty() && (!isMemoryAssociatedWithDate || associatedDate.isNotBlank())

    override fun asModel(): PhotoMemory {
        return PhotoMemory(
            id = id,
            photoContent = photoContent,
            addedAt = LocalDateTime.now(),
            associatedDate = if (!isMemoryAssociatedWithDate) null
            else dateTimePattern.parseDate(associatedDate)
        )
    }

    override fun fromModel(model: PhotoMemory): FormState<PhotoMemory> {
        val isAssociatedWithDate = model.associatedDate != null
        return copy(
            id = model.id,
            photoContent = model.photoContent,
            associatedDate = if (!isAssociatedWithDate) ""
            else dateTimePattern.formatDate(model.associatedDate!!)
        )
    }
}
