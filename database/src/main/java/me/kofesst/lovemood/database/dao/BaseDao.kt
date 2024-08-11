package me.kofesst.lovemood.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update

@Dao
interface BaseDao<Entity : Any> {
    @Insert
    suspend fun insert(entity: Entity): Long

    @Update
    suspend fun update(entity: Entity)

    @Delete
    suspend fun delete(entity: Entity)
}