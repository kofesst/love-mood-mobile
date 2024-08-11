package me.kofesst.lovemood.database

import androidx.room.Database
import androidx.room.RoomDatabase
import me.kofesst.lovemood.database.dao.ProfilesDao
import me.kofesst.lovemood.database.dao.RelationshipEventsDao
import me.kofesst.lovemood.database.dao.RelationshipsDao
import me.kofesst.lovemood.database.entities.ProfileEntity
import me.kofesst.lovemood.database.entities.RelationshipEntity
import me.kofesst.lovemood.database.entities.RelationshipEventEntity

@Database(
    entities = [
        ProfileEntity::class,
        RelationshipEntity::class,
        RelationshipEventEntity::class
    ],
    version = DatabaseConstants.VERSION
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getProfilesDao(): ProfilesDao
    abstract fun getRelationshipsDao(): RelationshipsDao
    abstract fun getRelationshipEventsDao(): RelationshipEventsDao
}