package me.kofesst.lovemood.database.entities

import androidx.room.Embedded
import androidx.room.Relation
import me.kofesst.lovemood.database.DatabaseTables

/**
 * Объединенная сущность отношений.
 *
 * [relationshipEntity] - сущность отношений.
 *
 * [userProfileEntity] - сущность профиля пользователя.
 *
 * [partnerProfileEntity] - сущность профиля партнёра.
 */
internal data class EmbeddedRelationship(
    @Embedded
    val relationshipEntity: RelationshipEntity,

    @Relation(
        entity = ProfileEntity::class,
        parentColumn = DatabaseTables.Relationships.USER_ID_COLUMN,
        entityColumn = DatabaseTables.Profiles.ID_COLUMN
    )
    val userProfileEntity: ProfileEntity,

    @Relation(
        entity = ProfileEntity::class,
        parentColumn = DatabaseTables.Relationships.PARTNER_ID_COLUMN,
        entityColumn = DatabaseTables.Profiles.ID_COLUMN
    )
    val partnerProfileEntity: ProfileEntity
)