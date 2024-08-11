package me.kofesst.lovemood.database.dao

import androidx.room.Dao
import androidx.room.Query
import me.kofesst.lovemood.database.DatabaseTables
import me.kofesst.lovemood.database.entities.RelationshipEventEntity

@Dao
interface RelationshipEventsDao : BaseDao<RelationshipEventEntity> {
    @Query("select * from ${DatabaseTables.RelationshipEvents.TABLE_NAME}")
    suspend fun selectAll(): List<RelationshipEventEntity>

    @Query(
        "select * from ${DatabaseTables.RelationshipEvents.TABLE_NAME} " +
                "where ${DatabaseTables.RelationshipEvents.ID_COLUMN} = :id"
    )
    suspend fun selectById(id: Int): RelationshipEventEntity?
}