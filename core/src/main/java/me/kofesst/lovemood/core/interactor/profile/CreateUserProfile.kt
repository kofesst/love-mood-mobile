package me.kofesst.lovemood.core.interactor.profile

import me.kofesst.lovemood.core.interactor.ParameterizedUseCase
import me.kofesst.lovemood.core.interactor.UseCaseParams
import me.kofesst.lovemood.core.models.Profile
import me.kofesst.lovemood.core.repository.ProfileRepository
import me.kofesst.lovemood.core.repository.SessionRepository
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Юзкейс создания профиля пользователя.
 */
@Singleton
class CreateUserProfile @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val sessionRepository: SessionRepository
) : ParameterizedUseCase<Profile, UseCaseParams.Single<Profile>>() {
    override val canResultBeNullable: Boolean = false

    override suspend fun execute(params: UseCaseParams.Single<Profile>): Profile {
        val profileId = profileRepository.createProfile(params.value)
        val session = sessionRepository.restore()
        sessionRepository.save(
            session.copy(profileId = profileId)
        )
        return params.value.copy(id = profileId)
    }
}