package me.kofesst.lovemood.presentation.forms.relationship

import me.kofesst.lovemood.core.models.Gender
import me.kofesst.lovemood.core.models.Profile
import me.kofesst.lovemood.core.models.Relationship
import me.kofesst.lovemood.core.text.AppTextHolder
import me.kofesst.lovemood.features.date.DateTimePattern
import me.kofesst.lovemood.presentation.forms.FormState
import java.time.LocalDate

private fun emptyProfile() = Profile(
    id = -1,
    username = "",
    gender = Gender.Male,
    dateOfBirth = LocalDate.now(),
    avatarContent = byteArrayOf()
)

data class RelationshipFormState(
    private val dateTimePattern: DateTimePattern,
    val id: Int = 0,
    val startDate: String = "",
    val startDateError: AppTextHolder? = null,
    val partnerProfile: Profile? = null
) : FormState<Relationship>() {
    companion object {
        val MIN_START_DATE = LocalDate.of(1900, 1, 1)
        val MAX_START_DATE = LocalDate.now()
    }

    override val isValid: Boolean = startDateError == null && partnerProfile != null

    override val isFilled: Boolean = startDate.isNotBlank()

    override fun asModel(): Relationship {
        return Relationship(
            id = id,
            userProfile = emptyProfile(),
            partnerProfile = partnerProfile!!,
            startDate = dateTimePattern.parseDate(startDate)
        )
    }

    override fun fromModel(model: Relationship): FormState<Relationship> {
        return copy(
            id = model.id,
            partnerProfile = model.partnerProfile,
            startDate = dateTimePattern.formatDate(model.startDate)
        )
    }
}