package me.kofesst.lovemood.database.dao

import androidx.room.Dao
import androidx.room.Query
import me.kofesst.lovemood.database.DatabaseTables
import me.kofesst.lovemood.database.entities.PhotoMemoryEntity

@Dao
interface PhotoMemoriesDao : BaseDao<PhotoMemoryEntity> {
    @Query("select * from ${DatabaseTables.PhotoMemories.TABLE_NAME}")
    suspend fun selectAll(): List<PhotoMemoryEntity>

    @Query(
        "select * from ${DatabaseTables.PhotoMemories.TABLE_NAME} " +
                "where ${DatabaseTables.PhotoMemories.ID_COLUMN} = :id"
    )
    suspend fun selectById(id: Int): PhotoMemoryEntity?
}