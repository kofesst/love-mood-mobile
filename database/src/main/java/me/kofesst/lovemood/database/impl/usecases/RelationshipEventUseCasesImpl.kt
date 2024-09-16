package me.kofesst.lovemood.database.impl.usecases

import me.kofesst.lovemood.core.models.events.RelationshipEvent
import me.kofesst.lovemood.core.usecases.crud.ReadAllUseCase
import me.kofesst.lovemood.core.usecases.crud.ReadByIdUseCase
import me.kofesst.lovemood.core.usecases.models.RelationshipEventUseCases
import me.kofesst.lovemood.database.dao.ProfilesDao
import me.kofesst.lovemood.database.dao.RelationshipEventsDao
import me.kofesst.lovemood.database.dao.RelationshipsDao
import me.kofesst.lovemood.database.entities.RelationshipEventEntity
import me.kofesst.lovemood.database.wrappers.RelationshipEventWrapper

@Deprecated(message = "Deprecated")
class RelationshipEventUseCasesImpl(
    profilesDao: ProfilesDao,
    relationshipsDao: RelationshipsDao,
    eventsDao: RelationshipEventsDao
) : BaseCrudUseCaseImpl<RelationshipEventEntity, RelationshipEventsDao, RelationshipEvent>(
    wrapper = RelationshipEventWrapper(profilesDao, relationshipsDao),
    dao = eventsDao
), RelationshipEventUseCases {
    override val readAll: ReadAllUseCase<RelationshipEvent> = ReadAllUseCase {
        val entities = eventsDao.selectAll()
        with(wrapper) { entities.map { it.asModel() } }
    }
    override val readById: ReadByIdUseCase<RelationshipEvent> = ReadByIdUseCase {
        val entity = eventsDao.selectById(it)
        with(wrapper) { entity?.asModel() }
    }
}