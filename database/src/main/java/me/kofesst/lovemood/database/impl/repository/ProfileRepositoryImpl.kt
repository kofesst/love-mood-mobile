package me.kofesst.lovemood.database.impl.repository

import me.kofesst.lovemood.core.models.Profile
import me.kofesst.lovemood.core.repository.ProfileRepository
import me.kofesst.lovemood.database.AppDatabase
import me.kofesst.lovemood.database.dao.ProfilesDao
import me.kofesst.lovemood.database.wrappers.ProfileWrapper.asEntity
import me.kofesst.lovemood.database.wrappers.ProfileWrapper.asModel
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Реализация репозитория профилей.
 */
@Singleton
internal class ProfileRepositoryImpl @Inject constructor(
    database: AppDatabase
) : ProfileRepository {
    private val profilesDao: ProfilesDao = database.getProfilesDao()

    override suspend fun getProfile(profileId: Int): Profile? {
        return profilesDao.selectById(profileId)?.asModel()
    }

    override suspend fun createProfile(profileData: Profile): Int {
        return profilesDao.insert(profileData.asEntity()).toInt()
    }

    override suspend fun updateProfile(profileData: Profile) {
        profilesDao.update(profileData.asEntity())
    }
}