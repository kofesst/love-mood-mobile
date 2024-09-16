package me.kofesst.lovemood.core.interactor.profile

import me.kofesst.lovemood.core.interactor.ParameterizedUseCase
import me.kofesst.lovemood.core.interactor.UseCaseParams
import me.kofesst.lovemood.core.models.Profile
import me.kofesst.lovemood.core.repository.ProfileRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UpdateUserProfile @Inject constructor(
    private val profileRepository: ProfileRepository
) : ParameterizedUseCase<Unit, UseCaseParams.Single<Profile>>() {
    override val canResultBeNullable: Boolean = false

    override suspend fun execute(params: UseCaseParams.Single<Profile>) {
        profileRepository.updateProfile(params.value)
    }
}