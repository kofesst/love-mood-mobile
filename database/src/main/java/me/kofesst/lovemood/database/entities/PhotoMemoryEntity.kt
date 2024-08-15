package me.kofesst.lovemood.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import me.kofesst.lovemood.database.DatabaseTables

@Suppress("ArrayInDataClass")
@Entity(tableName = DatabaseTables.PhotoMemories.TABLE_NAME)
data class PhotoMemoryEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(
        name = DatabaseTables.PhotoMemories.ID_COLUMN,
        typeAffinity = ColumnInfo.INTEGER,
        index = true
    )
    val id: Int = 0,

    @ColumnInfo(
        name = DatabaseTables.PhotoMemories.CONTENT_COLUMN,
        typeAffinity = ColumnInfo.BLOB
    )
    val photoContent: ByteArray = byteArrayOf(),

    @ColumnInfo(
        name = DatabaseTables.PhotoMemories.ADDED_DATE_TIME_COLUMN,
        typeAffinity = ColumnInfo.INTEGER
    )
    val addedAt: Long = 0,

    @ColumnInfo(
        name = DatabaseTables.PhotoMemories.LINKED_DATE_COLUMN,
        typeAffinity = ColumnInfo.INTEGER
    )
    val linkedDate: Long? = null
)