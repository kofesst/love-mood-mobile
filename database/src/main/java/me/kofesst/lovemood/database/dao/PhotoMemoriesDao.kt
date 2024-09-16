package me.kofesst.lovemood.database.dao

import androidx.room.Dao
import androidx.room.Query
import me.kofesst.lovemood.database.DatabaseTables
import me.kofesst.lovemood.database.entities.PhotoMemoryEntity

/**
 * Data access object сущности воспоминания.
 */
@Dao
internal interface PhotoMemoriesDao : BaseDao<PhotoMemoryEntity> {
    /**
     * Возвращает все сущности воспоминаний.
     */
    @Query("select * from ${DatabaseTables.PhotoMemories.TABLE_NAME}")
    suspend fun selectAll(): List<PhotoMemoryEntity>

    /**
     * Возвращает сущность воспоминания по ID [id].
     */
    @Query(
        "select * from ${DatabaseTables.PhotoMemories.TABLE_NAME} " +
                "where ${DatabaseTables.PhotoMemories.ID_COLUMN} = :id"
    )
    suspend fun selectById(id: Int): PhotoMemoryEntity?
}