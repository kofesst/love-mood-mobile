package me.kofesst.lovemood.core.interactor.memory

import me.kofesst.lovemood.core.interactor.ParameterizedUseCase
import me.kofesst.lovemood.core.interactor.UseCaseParams
import me.kofesst.lovemood.core.models.PhotoMemory
import me.kofesst.lovemood.core.repository.MemoriesRepository
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Юзкейс удаления воспоминания.
 */
@Singleton
class DeleteMemory @Inject constructor(
    private val memoriesRepository: MemoriesRepository
) : ParameterizedUseCase<Unit, UseCaseParams.Single<PhotoMemory>>() {
    override val canResultBeNullable: Boolean = false

    override suspend fun execute(params: UseCaseParams.Single<PhotoMemory>) {
        memoriesRepository.update(params.value)
    }
}