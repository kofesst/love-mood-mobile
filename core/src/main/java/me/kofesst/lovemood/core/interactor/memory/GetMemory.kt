package me.kofesst.lovemood.core.interactor.memory

import me.kofesst.lovemood.core.interactor.ParameterizedUseCase
import me.kofesst.lovemood.core.interactor.UseCaseParams
import me.kofesst.lovemood.core.models.PhotoMemory
import me.kofesst.lovemood.core.repository.MemoriesRepository
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Юзкейс получения воспоминания по ID модели.
 */
@Singleton
class GetMemory @Inject constructor(
    private val memoriesRepository: MemoriesRepository
) : ParameterizedUseCase<PhotoMemory, UseCaseParams.Single<Int>>() {
    override val canResultBeNullable: Boolean = true

    override suspend fun execute(params: UseCaseParams.Single<Int>): PhotoMemory? {
        return memoriesRepository.get(params.value)
    }
}