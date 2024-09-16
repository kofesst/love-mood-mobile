package me.kofesst.lovemood.database.impl.usecases

import me.kofesst.lovemood.core.models.Relationship
import me.kofesst.lovemood.core.usecases.crud.ReadAllUseCase
import me.kofesst.lovemood.core.usecases.crud.ReadByIdUseCase
import me.kofesst.lovemood.core.usecases.models.RelationshipUseCases
import me.kofesst.lovemood.core.usecases.models.relationship.ReadRelationshipByProfileIdUseCase
import me.kofesst.lovemood.database.dao.ProfilesDao
import me.kofesst.lovemood.database.dao.RelationshipsDao
import me.kofesst.lovemood.database.entities.RelationshipEntity
import me.kofesst.lovemood.database.wrappers.RelationshipWrapper

@Deprecated(message = "Deprecated")
class RelationshipUseCasesImpl(
    profilesDao: ProfilesDao,
    relationshipsDao: RelationshipsDao
) : BaseCrudUseCaseImpl<RelationshipEntity, RelationshipsDao, Relationship>(
    wrapper = RelationshipWrapper(profilesDao),
    dao = relationshipsDao
), RelationshipUseCases {
    override val readAll: ReadAllUseCase<Relationship> = ReadAllUseCase {
        val entities = relationshipsDao.selectAll()
        with(wrapper) { entities.map { it.asModel() } }
    }
    override val readById: ReadByIdUseCase<Relationship> = ReadByIdUseCase { relationshipId ->
        val entity = relationshipsDao.selectById(relationshipId)
        with(wrapper) { entity?.asModel() }
    }

    override val readByProfileId: ReadRelationshipByProfileIdUseCase =
        ReadRelationshipByProfileIdUseCase { profileId ->
            val entity = relationshipsDao.selectByProfileId(profileId)
            with(wrapper) { entity?.asModel() }
        }
}