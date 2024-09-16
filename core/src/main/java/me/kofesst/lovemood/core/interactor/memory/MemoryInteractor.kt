package me.kofesst.lovemood.core.interactor.memory

import javax.inject.Inject
import javax.inject.Singleton

/**
 * Интерактор воспоминаний.
 */
@Singleton
class MemoryInteractor @Inject constructor(
    val getAll: GetAllMemories,
    val create: CreateMemory,
    val update: UpdateMemory,
    val delete: DeleteMemory
)