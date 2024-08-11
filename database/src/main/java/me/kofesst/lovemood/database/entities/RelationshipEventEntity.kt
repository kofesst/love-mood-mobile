package me.kofesst.lovemood.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import me.kofesst.lovemood.database.DatabaseTables

@Entity(
    tableName = DatabaseTables.RelationshipEvents.TABLE_NAME,
    foreignKeys = [
        ForeignKey(
            entity = RelationshipEntity::class,
            parentColumns = [DatabaseTables.Relationships.ID_COLUMN],
            childColumns = [DatabaseTables.RelationshipEvents.RELATIONSHIP_ID_COLUMN]
        )
    ]
)
data class RelationshipEventEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(
        name = DatabaseTables.RelationshipEvents.ID_COLUMN,
        typeAffinity = ColumnInfo.INTEGER,
        index = true
    )
    val id: Int = 0,

    @ColumnInfo(
        name = DatabaseTables.RelationshipEvents.NAME_COLUMN,
        typeAffinity = ColumnInfo.TEXT
    )
    val rawName: String = "",

    @ColumnInfo(
        name = DatabaseTables.RelationshipEvents.DAY_OF_MONTH_COLUMN,
        typeAffinity = ColumnInfo.INTEGER
    )
    val dayOfMonth: Int = 1,

    @ColumnInfo(
        name = DatabaseTables.RelationshipEvents.MONTH_COLUMN,
        typeAffinity = ColumnInfo.INTEGER
    )
    val monthValue: Int = 1,

    @ColumnInfo(
        name = DatabaseTables.RelationshipEvents.RELATIONSHIP_ID_COLUMN,
        typeAffinity = ColumnInfo.INTEGER,
        index = true
    )
    val relationshipId: Int = 0
)
