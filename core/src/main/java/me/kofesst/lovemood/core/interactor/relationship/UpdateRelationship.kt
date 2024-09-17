package me.kofesst.lovemood.core.interactor.relationship

import me.kofesst.lovemood.core.interactor.ParameterizedUseCase
import me.kofesst.lovemood.core.interactor.UseCaseParams
import me.kofesst.lovemood.core.models.Relationship
import me.kofesst.lovemood.core.repository.ProfileRepository
import me.kofesst.lovemood.core.repository.RelationshipRepository
import me.kofesst.lovemood.core.repository.SessionRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UpdateRelationship @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val relationshipRepository: RelationshipRepository,
    private val sessionRepository: SessionRepository
) : ParameterizedUseCase<Unit, UseCaseParams.Single<Relationship>>() {
    override val canResultBeNullable: Boolean = false

    override suspend fun execute(params: UseCaseParams.Single<Relationship>) {
        val userProfileId = sessionRepository.restore().profileId ?: return
        profileRepository.updateProfile(params.value.partnerProfile)
        relationshipRepository.updateRelationship(
            relationshipData = params.value.copy(
                userProfile = params.value.userProfile.copy(
                    id = userProfileId
                )
            )
        )
    }
}