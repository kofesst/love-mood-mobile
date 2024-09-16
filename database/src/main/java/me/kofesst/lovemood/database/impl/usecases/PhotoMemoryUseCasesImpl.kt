package me.kofesst.lovemood.database.impl.usecases

import me.kofesst.lovemood.core.models.PhotoMemory
import me.kofesst.lovemood.core.usecases.crud.ReadAllUseCase
import me.kofesst.lovemood.core.usecases.crud.ReadByIdUseCase
import me.kofesst.lovemood.core.usecases.models.PhotoMemoryUseCases
import me.kofesst.lovemood.database.dao.PhotoMemoriesDao
import me.kofesst.lovemood.database.entities.PhotoMemoryEntity
import me.kofesst.lovemood.database.wrappers.PhotoMemoryWrapper

@Deprecated(message = "Deprecated")
class PhotoMemoryUseCasesImpl(
    photoMemoriesDao: PhotoMemoriesDao
) : BaseCrudUseCaseImpl<PhotoMemoryEntity, PhotoMemoriesDao, PhotoMemory>(
    wrapper = PhotoMemoryWrapper,
    dao = photoMemoriesDao
), PhotoMemoryUseCases {
    override val readAll: ReadAllUseCase<PhotoMemory> = ReadAllUseCase {
        val entities = photoMemoriesDao.selectAll()
        with(wrapper) { entities.map { it.asModel() } }
    }
    override val readById: ReadByIdUseCase<PhotoMemory> = ReadByIdUseCase {
        val entity = photoMemoriesDao.selectById(it)
        with(wrapper) { entity?.asModel() }
    }
}