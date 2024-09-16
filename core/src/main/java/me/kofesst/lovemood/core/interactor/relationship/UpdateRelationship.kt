package me.kofesst.lovemood.core.interactor.relationship

import me.kofesst.lovemood.core.interactor.ParameterizedUseCase
import me.kofesst.lovemood.core.interactor.UseCaseParams
import me.kofesst.lovemood.core.models.Relationship
import me.kofesst.lovemood.core.repository.ProfileRepository
import me.kofesst.lovemood.core.repository.RelationshipRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UpdateRelationship @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val relationshipRepository: RelationshipRepository
) : ParameterizedUseCase<Unit, UseCaseParams.Single<Relationship>>() {
    override val canResultBeNullable: Boolean = false

    override suspend fun execute(params: UseCaseParams.Single<Relationship>) {
        profileRepository.updateProfile(params.value.partnerProfile)
        relationshipRepository.updateRelationship(params.value)
    }
}