package me.kofesst.lovemood.core.interactor.profile

import me.kofesst.lovemood.core.interactor.UseCase
import me.kofesst.lovemood.core.models.Profile
import me.kofesst.lovemood.core.repository.ProfileRepository
import me.kofesst.lovemood.core.repository.SessionRepository
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Юзкейс получения профиля пользователя.
 */
@Singleton
class GetUserProfile @Inject constructor(
    private val sessionRepository: SessionRepository,
    private val profileRepository: ProfileRepository
) : UseCase<Profile>() {
    override val canResultBeNullable: Boolean = true

    override suspend fun execute(): Profile? {
        return sessionRepository.restore().profileId?.let {
            profileRepository.getProfile(it)
        }
    }
}