package me.kofesst.lovemood.database.dao

import androidx.room.Dao
import androidx.room.Query
import me.kofesst.lovemood.database.DatabaseTables
import me.kofesst.lovemood.database.entities.ProfileEntity

/**
 * Data access object сущности профиля.
 */
@Dao
interface ProfilesDao : BaseDao<ProfileEntity> {
    /**
     * Возвращает сущность профиля по ID [id].
     */
    @Query(
        "select * from ${DatabaseTables.Profiles.TABLE_NAME} " +
                "where ${DatabaseTables.Profiles.ID_COLUMN} = :id"
    )
    suspend fun selectById(id: Int): ProfileEntity?
}