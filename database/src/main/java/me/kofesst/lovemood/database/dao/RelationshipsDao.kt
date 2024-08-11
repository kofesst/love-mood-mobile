package me.kofesst.lovemood.database.dao

import androidx.room.Dao
import androidx.room.Query
import me.kofesst.lovemood.database.DatabaseTables
import me.kofesst.lovemood.database.entities.RelationshipEntity

@Dao
interface RelationshipsDao : BaseDao<RelationshipEntity> {
    @Query("select * from ${DatabaseTables.Relationships.TABLE_NAME}")
    suspend fun selectAll(): List<RelationshipEntity>

    @Query(
        "select * from ${DatabaseTables.Relationships.TABLE_NAME} " +
                "where ${DatabaseTables.Relationships.ID_COLUMN} = :id"
    )
    suspend fun selectById(id: Int): RelationshipEntity?

    @Query(
        "select * from ${DatabaseTables.Relationships.TABLE_NAME} where " +
                "${DatabaseTables.Relationships.USER_ID_COLUMN} = :profileId or " +
                "${DatabaseTables.Relationships.PARTNER_ID_COLUMN} = :profileId "
    )
    suspend fun selectByProfileId(profileId: Int): RelationshipEntity?
}