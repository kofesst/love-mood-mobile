package me.kofesst.lovemood.core.interactor.profile

import javax.inject.Inject
import javax.inject.Singleton

/**
 * Интерактор профиля пользователя.
 */
@Singleton
class UserProfileInteractor @Inject constructor(
    val get: GetUserProfile,
    val create: CreateUserProfile,
    val update: UpdateUserProfile
)