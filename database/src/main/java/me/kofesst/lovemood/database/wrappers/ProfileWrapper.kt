package me.kofesst.lovemood.database.wrappers

import me.kofesst.lovemood.core.models.Gender
import me.kofesst.lovemood.core.models.Profile
import me.kofesst.lovemood.database.entities.ProfileEntity
import me.kofesst.lovemood.features.date.epochMillis
import me.kofesst.lovemood.features.date.localDate

object ProfileWrapper : EntityWrapper<ProfileEntity, Profile> {
    override suspend fun Profile.asEntity(): ProfileEntity {
        return ProfileEntity(
            id = id,
            username = username,
            genderName = gender.name,
            dateOfBirthMillis = dateOfBirth.epochMillis,
            avatarContent = avatarContent
        )
    }

    override suspend fun ProfileEntity.asModel(): Profile {
        return Profile(
            id = id,
            username = username,
            gender = Gender.valueOf(genderName),
            dateOfBirth = dateOfBirthMillis.localDate,
            avatarContent = avatarContent
        )
    }
}