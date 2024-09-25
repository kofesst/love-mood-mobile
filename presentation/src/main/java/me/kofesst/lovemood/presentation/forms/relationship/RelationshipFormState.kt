package me.kofesst.lovemood.presentation.forms.relationship

import me.kofesst.lovemood.core.models.Profile
import me.kofesst.lovemood.core.models.Relationship
import me.kofesst.lovemood.localization.text.TextBuilder
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
    val id: Int = 0,
    val startDate: LocalDate = RelationshipFormValidator.MAX_START_DATE,
    val startDateError: TextBuilder? = null,
    val partnerProfile: ProfileFormState = ProfileFormState()
) : FormState<Relationship>() {
    override val isValid: Boolean = startDateError == null && partnerProfile.isValid

    override val isFilled: Boolean = true

    override fun asModel(): Relationship {
        return Relationship(
            id = id,
            userProfile = emptyProfile(),
            partnerProfile = partnerProfile.asModel(),
            startDate = startDate
        )
    }

    override fun fromModel(model: Relationship) = copy(
        id = model.id,
        partnerProfile = partnerProfile.fromModel(model.partnerProfile),
        startDate = model.startDate
    )
}