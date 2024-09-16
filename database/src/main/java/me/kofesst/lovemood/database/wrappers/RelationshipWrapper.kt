package me.kofesst.lovemood.database.wrappers

import me.kofesst.lovemood.core.models.Relationship
import me.kofesst.lovemood.database.ProfileNotFoundException
import me.kofesst.lovemood.database.dao.ProfilesDao
import me.kofesst.lovemood.database.entities.RelationshipEntity
import me.kofesst.lovemood.features.date.epochMillis
import me.kofesst.lovemood.features.date.localDate

@Deprecated(message = "Deprecated")
class RelationshipWrapper(
    private val profilesDao: ProfilesDao
) : EntityWrapper<RelationshipEntity, Relationship> {
    override suspend fun Relationship.asEntity(): RelationshipEntity {
        return RelationshipEntity(
            id = id,
            userProfileId = userProfile.id,
            partnerProfileId = partnerProfile.id,
            startDateMillis = startDate.epochMillis
        )
    }

    override suspend fun RelationshipEntity.asModel(): Relationship {
        val userProfile = profilesDao.selectById(userProfileId)
            ?: throw ProfileNotFoundException()
        val partnerProfile = profilesDao.selectById(partnerProfileId)
            ?: throw ProfileNotFoundException()
        return with(ProfileWrapper) {
            Relationship(
                id = id,
                userProfile = userProfile.asModel(),
                partnerProfile = partnerProfile.asModel(),
                startDate = startDateMillis.localDate
            )
        }
    }
}