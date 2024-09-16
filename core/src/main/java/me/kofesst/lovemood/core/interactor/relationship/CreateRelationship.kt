package me.kofesst.lovemood.core.interactor.relationship

import me.kofesst.lovemood.core.interactor.ParameterizedUseCase
import me.kofesst.lovemood.core.interactor.UseCaseParams
import me.kofesst.lovemood.core.models.Relationship
import me.kofesst.lovemood.core.repository.ProfileRepository
import me.kofesst.lovemood.core.repository.RelationshipRepository
import me.kofesst.lovemood.core.repository.SessionRepository
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Юзкейс создания отношений.
 */
@Singleton
class CreateRelationship @Inject constructor(
    private val sessionRepository: SessionRepository,
    private val profileRepository: ProfileRepository,
    private val relationshipRepository: RelationshipRepository
) : ParameterizedUseCase<Int, UseCaseParams.Single<Relationship>>() {
    override val canResultBeNullable: Boolean = true

    override suspend fun execute(params: UseCaseParams.Single<Relationship>): Int? {
        var relationship = params.value
        profileRepository.createProfile(relationship.partnerProfile).also { newPartnerProfileId ->
            relationship = relationship.copy(
                partnerProfile = relationship.partnerProfile.copy(
                    id = newPartnerProfileId
                )
            )
        }
        val relationshipId = relationshipRepository.createRelationship(relationship)
        val session = sessionRepository.restore()
        sessionRepository.save(
            session.copy(relationshipId = relationshipId)
        )
        return relationshipId
    }
}