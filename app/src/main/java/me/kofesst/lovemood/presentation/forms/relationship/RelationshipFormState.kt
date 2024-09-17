package me.kofesst.lovemood.presentation.forms.relationship

import me.kofesst.lovemood.core.models.Profile
import me.kofesst.lovemood.core.models.Relationship
import me.kofesst.lovemood.core.text.AppTextHolder
import me.kofesst.lovemood.features.date.DateTimePattern
import me.kofesst.lovemood.presentation.forms.FormState
import me.kofesst.lovemood.presentation.forms.profile.ProfileFormState
import java.time.LocalDate

private fun emptyProfile() = Profile(
    id = -1,
    username = "",
    dateOfBirth = LocalDate.now(),
    avatarContent = byteArrayOf()
)

data class RelationshipFormState(
    private val dateTimePattern: DateTimePattern,
    val id: Int = 0,
    val startDate: String = "",
    val startDateError: AppTextHolder? = null,
    val partnerProfile: ProfileFormState = ProfileFormState(dateTimePattern)
) : FormState<Relationship>() {
    override val isValid: Boolean = startDateError == null && partnerProfile.isValid

    override val isFilled: Boolean = startDate.isNotBlank()

    override fun asModel(): Relationship {
        return Relationship(
            id = id,
            userProfile = emptyProfile(),
            partnerProfile = partnerProfile.asModel(),
            startDate = dateTimePattern.parseDate(startDate)
        )
    }

    override fun fromModel(model: Relationship) = copy(
        id = model.id,
        partnerProfile = partnerProfile.fromModel(model.partnerProfile),
        startDate = dateTimePattern.formatDate(model.startDate)
    )
}