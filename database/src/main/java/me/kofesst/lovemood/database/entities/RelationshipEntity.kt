package me.kofesst.lovemood.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import me.kofesst.lovemood.database.DatabaseTables

@Entity(
    tableName = DatabaseTables.Relationships.TABLE_NAME,
    foreignKeys = [
        ForeignKey(
            entity = ProfileEntity::class,
            parentColumns = [DatabaseTables.Profiles.ID_COLUMN],
            childColumns = [DatabaseTables.Relationships.USER_ID_COLUMN]
        ),
        ForeignKey(
            entity = ProfileEntity::class,
            parentColumns = [DatabaseTables.Profiles.ID_COLUMN],
            childColumns = [DatabaseTables.Relationships.PARTNER_ID_COLUMN]
        )
    ]
)
data class RelationshipEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(
        name = DatabaseTables.Relationships.ID_COLUMN,
        typeAffinity = ColumnInfo.INTEGER,
        index = true
    )
    val id: Int = 0,

    @ColumnInfo(
        name = DatabaseTables.Relationships.USER_ID_COLUMN,
        typeAffinity = ColumnInfo.INTEGER,
        index = true
    )
    val userProfileId: Int = 0,

    @ColumnInfo(
        name = DatabaseTables.Relationships.PARTNER_ID_COLUMN,
        typeAffinity = ColumnInfo.INTEGER,
        index = true
    )
    val partnerProfileId: Int = 0,

    @ColumnInfo(
        name = DatabaseTables.Relationships.START_DATE_COLUMN,
        typeAffinity = ColumnInfo.INTEGER
    )
    val startDateMillis: Long = 0
)
