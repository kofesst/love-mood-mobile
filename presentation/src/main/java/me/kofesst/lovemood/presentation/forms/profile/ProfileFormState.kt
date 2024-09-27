package me.kofesst.lovemood.presentation.forms.profile

import me.kofesst.lovemood.core.models.Profile
import me.kofesst.lovemood.localization.text.TextBuilder
import me.kofesst.lovemood.presentation.forms.FormState
import java.time.LocalDate

@Suppress("ArrayInDataClass")
data class ProfileFormState(
    val id: Int = 0,
    val avatarContent: ByteArray = byteArrayOf(),
    val username: String = "",
    val usernameError: TextBuilder? = null,
    val dateOfBirth: LocalDate = ProfileFormValidator.MAX_DATE_OF_BRITH,
    val dateOfBirthError: TextBuilder? = null
) : FormState<Profile>() {
    override val isValid
        get() = usernameError == null && dateOfBirthError == null

    override val isFilled: Boolean
        get() = username.isNotBlank()

    override fun asModel() = Profile(
        id = id,
        username = username,
        dateOfBirth = dateOfBirth,
        avatarContent = avatarContent
    )

    override fun fromModel(model: Profile) = copy(
        id = model.id,
        avatarContent = model.avatarContent,
        username = model.username,
        usernameError = null,
        dateOfBirth = model.dateOfBirth,
        dateOfBirthError = null
    )
}
