package me.kofesst.lovemood.database.impl.repository

import me.kofesst.lovemood.core.models.PhotoMemory
import me.kofesst.lovemood.core.repository.MemoriesRepository
import me.kofesst.lovemood.database.AppDatabase
import me.kofesst.lovemood.database.wrappers.PhotoMemoryWrapper.asEntity
import me.kofesst.lovemood.database.wrappers.PhotoMemoryWrapper.asModel
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Реализация репозитория воспоминаний.
 */
@Singleton
internal class MemoryRepositoryImpl @Inject constructor(
    database: AppDatabase
) : MemoriesRepository {
    private val memoriesDao = database.getPhotoMemoriesDao()

    override suspend fun getAll(): List<PhotoMemory> {
        return memoriesDao.selectAll().map { it.asModel() }
    }

    override suspend fun get(id: Int): PhotoMemory? {
        return memoriesDao.selectById(id)?.asModel()
    }

    override suspend fun create(memoryData: PhotoMemory): Int {
        return memoriesDao.insert(memoryData.asEntity()).toInt()
    }

    override suspend fun update(memoryData: PhotoMemory) {
        memoriesDao.update(memoryData.asEntity())
    }

    override suspend fun delete(memoryData: PhotoMemory) {
        memoriesDao.delete(memoryData.asEntity())
    }
}