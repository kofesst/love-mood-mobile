package me.kofesst.lovemood.database.dao

import androidx.room.Dao
import androidx.room.Query
import me.kofesst.lovemood.database.DatabaseTables
import me.kofesst.lovemood.database.entities.ProfileEntity

@Dao
interface ProfilesDao : BaseDao<ProfileEntity> {
    @Query("select * from ${DatabaseTables.Profiles.TABLE_NAME}")
    suspend fun selectAll(): List<ProfileEntity>

    @Query(
        "select * from ${DatabaseTables.Profiles.TABLE_NAME} " +
                "where ${DatabaseTables.Profiles.ID_COLUMN} = :id"
    )
    suspend fun selectById(id: Int): ProfileEntity?
}