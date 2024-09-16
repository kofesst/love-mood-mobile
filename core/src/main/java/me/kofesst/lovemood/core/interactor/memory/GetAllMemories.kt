package me.kofesst.lovemood.core.interactor.memory

import me.kofesst.lovemood.core.interactor.UseCase
import me.kofesst.lovemood.core.models.PhotoMemory
import me.kofesst.lovemood.core.repository.MemoriesRepository
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Юзкейс получения всех воспоминаний.
 */
@Singleton
class GetAllMemories @Inject constructor(
    private val memoriesRepository: MemoriesRepository
) : UseCase<List<PhotoMemory>>() {
    override val canResultBeNullable: Boolean = false

    override suspend fun execute(): List<PhotoMemory> {
        return memoriesRepository.getAll()
    }
}