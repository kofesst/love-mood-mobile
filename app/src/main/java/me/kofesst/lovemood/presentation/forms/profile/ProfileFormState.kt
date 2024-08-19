package me.kofesst.lovemood.presentation.forms.profile

import me.kofesst.lovemood.core.models.Gender
import me.kofesst.lovemood.core.models.Profile
import me.kofesst.lovemood.core.text.AppTextHolder
import me.kofesst.lovemood.features.date.DateTimePattern
import me.kofesst.lovemood.presentation.forms.FormState
import java.time.LocalDate

@Suppress("ArrayInDataClass")
data class ProfileFormState(
    private val dateTimePattern: DateTimePattern,
    val id: Int = 0,
    val avatarContent: ByteArray = byteArrayOf(),
    val username: String = "",
    val usernameError: AppTextHolder? = null,
    val gender: Gender = Gender.Male,
    val dateOfBirth: String = "",
    val dateOfBirthError: AppTextHolder? = null
) : FormState<Profile>() {
    companion object {
        val USERNAME_LENGTH_RANGE = 2..48
        val MIN_DATE_OF_BIRTH: LocalDate = LocalDate.of(1900, 1, 1)
        val MAX_DATE_OF_BRITH: LocalDate = LocalDate.now()
    }

    override val isValid
        get() = usernameError == null && dateOfBirthError == null

    override val isFilled: Boolean
        get() = username.isNotBlank() && dateOfBirth.isNotBlank()

    override fun asModel() = Profile(
        id = id,
        username = username,
        gender = gender,
        dateOfBirth = dateTimePattern.parseDate(dateOfBirth),
        avatarContent = avatarContent
    )

    override fun fromModel(model: Profile) = copy(
        id = model.id,
        avatarContent = model.avatarContent,
        username = model.username,
        usernameError = null,
        gender = model.gender,
        dateOfBirth = dateTimePattern.formatDate(model.dateOfBirth),
        dateOfBirthError = null
    )
}
