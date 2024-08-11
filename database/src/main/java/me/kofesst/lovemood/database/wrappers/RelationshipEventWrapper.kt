package me.kofesst.lovemood.database.wrappers

import me.kofesst.lovemood.core.models.events.MonthlyRelationshipEvent
import me.kofesst.lovemood.core.models.events.RelationshipEvent
import me.kofesst.lovemood.core.models.events.YearlyRelationshipEvent
import me.kofesst.lovemood.core.text.AppTextHolder
import me.kofesst.lovemood.database.RelationshipEventNotFoundException
import me.kofesst.lovemood.database.RelationshipNotFoundException
import me.kofesst.lovemood.database.dao.ProfilesDao
import me.kofesst.lovemood.database.dao.RelationshipsDao
import me.kofesst.lovemood.database.entities.RelationshipEventEntity

class RelationshipEventWrapper(
    private val profilesDao: ProfilesDao,
    private val relationshipsDao: RelationshipsDao
) : EntityWrapper<RelationshipEventEntity, RelationshipEvent> {
    override suspend fun RelationshipEvent.asEntity(): RelationshipEventEntity {
        val dayOfMonth = (this as? MonthlyRelationshipEvent)?.dayOfMonth ?: -1
        val monthValue = (this as? YearlyRelationshipEvent)?.monthValue ?: -1
        return RelationshipEventEntity(
            id = id,
            rawName = nameHolder.string(),
            dayOfMonth = dayOfMonth,
            monthValue = monthValue,
            relationshipId = relationship.id
        )
    }

    override suspend fun RelationshipEventEntity.asModel(): RelationshipEvent {
        val relationship = relationshipsDao.selectById(relationshipId)
            ?: throw RelationshipNotFoundException()
        return with(RelationshipWrapper(profilesDao)) {
            when {
                monthValue > 0 -> {
                    YearlyRelationshipEvent(
                        id = id,
                        dayOfMonth = dayOfMonth,
                        monthValue = monthValue,
                        isDefault = false,
                        nameHolder = AppTextHolder.raw(rawName),
                        relationship = relationship.asModel()
                    )
                }

                dayOfMonth > 0 -> {
                    MonthlyRelationshipEvent(
                        id = id,
                        dayOfMonth = dayOfMonth,
                        isDefault = false,
                        nameHolder = AppTextHolder.raw(rawName),
                        relationship = relationship.asModel()
                    )
                }

                else -> throw RelationshipEventNotFoundException()
            }
        }
    }
}