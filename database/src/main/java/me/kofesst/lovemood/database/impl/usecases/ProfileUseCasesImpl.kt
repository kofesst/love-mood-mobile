package me.kofesst.lovemood.database.impl.usecases

import me.kofesst.lovemood.core.models.Profile
import me.kofesst.lovemood.core.usecases.crud.ReadAllUseCase
import me.kofesst.lovemood.core.usecases.crud.ReadByIdUseCase
import me.kofesst.lovemood.core.usecases.models.ProfileUseCases
import me.kofesst.lovemood.database.dao.ProfilesDao
import me.kofesst.lovemood.database.entities.ProfileEntity
import me.kofesst.lovemood.database.wrappers.ProfileWrapper

@Deprecated(message = "Deprecated")
class ProfileUseCasesImpl(
    profilesDao: ProfilesDao
) : BaseCrudUseCaseImpl<ProfileEntity, ProfilesDao, Profile>(
    wrapper = ProfileWrapper,
    dao = profilesDao
), ProfileUseCases {
    override val readAll: ReadAllUseCase<Profile> = ReadAllUseCase {
        val entities = profilesDao.selectAll()
        with(wrapper) { entities.map { it.asModel() } }
    }
    override val readById: ReadByIdUseCase<Profile> = ReadByIdUseCase {
        val entity = profilesDao.selectById(it)
        with(wrapper) { entity?.asModel() }
    }
}