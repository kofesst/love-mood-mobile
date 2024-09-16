package me.kofesst.lovemood.core.repository

import me.kofesst.lovemood.core.models.Profile

/**
 * Репозиторий данных профилей.
 */
interface ProfileRepository {
    /**
     * Возвращает профиль по его ID [profileId].
     *
     * Если профиль не найден, возвращает null.
     */
    suspend fun getProfile(profileId: Int): Profile?

    /**
     * Создает профиль и возвращает ID созданной модели.
     *
     * [profileData] - данные профиля.
     */
    suspend fun createProfile(profileData: Profile): Int

    /**
     * Обновляет профиль.
     *
     * [profileData] - данные профиля.
     */
    suspend fun updateProfile(profileData: Profile)
}