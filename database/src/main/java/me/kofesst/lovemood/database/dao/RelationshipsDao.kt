package me.kofesst.lovemood.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import me.kofesst.lovemood.database.DatabaseTables
import me.kofesst.lovemood.database.entities.EmbeddedRelationship
import me.kofesst.lovemood.database.entities.RelationshipEntity

/**
 * Data access object сущности отношений.
 */
@Dao
interface RelationshipsDao : BaseDao<RelationshipEntity> {
    /**
     * Возвращает объединенную сущность отношений по ID [id].
     */
    @Transaction
    @Query(
        "select * from ${DatabaseTables.Relationships.TABLE_NAME} " +
                "where ${DatabaseTables.Relationships.ID_COLUMN} = :id"
    )
    suspend fun selectById(id: Int): EmbeddedRelationship?
}