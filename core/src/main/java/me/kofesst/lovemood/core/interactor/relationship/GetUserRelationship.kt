package me.kofesst.lovemood.core.interactor.relationship

import me.kofesst.lovemood.core.interactor.UseCase
import me.kofesst.lovemood.core.models.Relationship
import me.kofesst.lovemood.core.repository.RelationshipRepository
import me.kofesst.lovemood.core.repository.SessionRepository
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Юзкейс получения отношений пользователя.
 */
@Singleton
class GetUserRelationship @Inject constructor(
    private val sessionRepository: SessionRepository,
    private val relationshipRepository: RelationshipRepository
) : UseCase<Relationship>() {
    override val canResultBeNullable: Boolean = true

    override suspend fun execute(): Relationship? {
        val session = sessionRepository.restore()
        return session.relationshipId?.let {
            relationshipRepository.getRelationship(it)
        }
    }
}