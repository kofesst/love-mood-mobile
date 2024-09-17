package me.kofesst.lovemood.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import me.kofesst.lovemood.database.DatabaseTables

/**
 * Сущность профиля.
 */
@Suppress("ArrayInDataClass")
@Entity(tableName = DatabaseTables.Profiles.TABLE_NAME)
internal data class ProfileEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(
        name = DatabaseTables.Profiles.ID_COLUMN,
        typeAffinity = ColumnInfo.INTEGER,
        index = true
    )
    val id: Int = 0,

    @ColumnInfo(
        name = DatabaseTables.Profiles.USERNAME_COLUMN,
        typeAffinity = ColumnInfo.TEXT
    )
    val username: String = "",

    @ColumnInfo(
        name = DatabaseTables.Profiles.DATE_OF_BIRTH_COLUMN,
        typeAffinity = ColumnInfo.INTEGER
    )
    val dateOfBirthMillis: Long = 0,

    @ColumnInfo(
        name = DatabaseTables.Profiles.AVATAR_COLUMN,
        typeAffinity = ColumnInfo.BLOB
    )
    val avatarContent: ByteArray = byteArrayOf()
)
