package me.kofesst.lovemood.database.impl.repository

import me.kofesst.lovemood.core.models.Relationship
import me.kofesst.lovemood.core.repository.RelationshipRepository
import me.kofesst.lovemood.database.AppDatabase
import me.kofesst.lovemood.database.wrappers.EmbeddedRelationshipWrapper.asEntity
import me.kofesst.lovemood.database.wrappers.EmbeddedRelationshipWrapper.asModel
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Реализация репозитория отношений.
 */
@Singleton
internal class RelationshipRepositoryImpl @Inject constructor(
    database: AppDatabase
) : RelationshipRepository {
    private val relationshipsDao = database.getRelationshipsDao()

    override suspend fun getRelationship(relationshipId: Int): Relationship? {
        return relationshipsDao.embedById(relationshipId)?.asModel()
    }

    override suspend fun createRelationship(relationshipData: Relationship): Int {
        return relationshipsDao.insert(relationshipData.asEntity()).toInt()
    }

    override suspend fun updateRelationship(relationshipData: Relationship) {
        relationshipsDao.update(relationshipData.asEntity())
    }
}