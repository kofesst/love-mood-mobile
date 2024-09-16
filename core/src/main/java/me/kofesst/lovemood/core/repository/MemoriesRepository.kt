package me.kofesst.lovemood.core.repository

import me.kofesst.lovemood.core.models.PhotoMemory

/**
 * Репозиторий воспоминаний.
 */
interface MemoriesRepository {
    /**
     * Возвращает список всех воспоминаний.
     */
    suspend fun getAll(): List<PhotoMemory>

    /**
     * Создает воспоминание и возвращает ID созданной модели.
     *
     * [memoryData] - данные воспоминания.
     */
    suspend fun create(memoryData: PhotoMemory): Int

    /**
     * Обновляет воспоминание.
     *
     * [memoryData] - данные воспоминания.
     */
    suspend fun update(memoryData: PhotoMemory)

    /**
     * Удаляет воспоминание.
     *
     * [memoryData] - данные воспоминания.
     */
    suspend fun delete(memoryData: PhotoMemory)
}