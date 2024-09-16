package me.kofesst.lovemood.database.wrappers

import me.kofesst.lovemood.core.models.Relationship
import me.kofesst.lovemood.database.entities.EmbeddedRelationship
import me.kofesst.lovemood.database.entities.RelationshipEntity
import me.kofesst.lovemood.features.date.epochMillis
import me.kofesst.lovemood.features.date.localDate

/**
 * Преобразователь моделей и сущностей отношений.
 */
object RelationshipWrapper {
    fun Relationship.asEntity(): RelationshipEntity {
        return RelationshipEntity(
            id = id,
            userProfileId = userProfile.id,
            partnerProfileId = partnerProfile.id,
            startDateMillis = startDate.epochMillis
        )
    }

    fun EmbeddedRelationship.asModel(): Relationship {
        return Relationship(
            id = relationshipEntity.id,
            userProfile = with(ProfileWrapper) { userProfileEntity.asModel() },
            partnerProfile = with(ProfileWrapper) { partnerProfileEntity.asModel() },
            startDate = relationshipEntity.startDateMillis.localDate
        )
    }
}