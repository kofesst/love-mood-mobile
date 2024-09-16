package me.kofesst.lovemood.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update

/**
 * Базовый для всех сущностей Data access object
 */
@Dao
interface BaseDao<Entity : Any> {
    /**
     * Добавляет сущность [entity] в базу данных.
     *
     * Возвращает ID новой сущности.
     */
    @Insert
    suspend fun insert(entity: Entity): Long

    /**
     * Обновляет сущность [entity].
     */
    @Update
    suspend fun update(entity: Entity)

    /**
     * Удаляет сущность [entity].
     */
    @Delete
    suspend fun delete(entity: Entity)
}