package me.kofesst.lovemood.database

import androidx.room.Database
import androidx.room.RoomDatabase
import me.kofesst.lovemood.database.dao.PhotoMemoriesDao
import me.kofesst.lovemood.database.dao.ProfilesDao
import me.kofesst.lovemood.database.dao.RelationshipsDao
import me.kofesst.lovemood.database.entities.PhotoMemoryEntity
import me.kofesst.lovemood.database.entities.ProfileEntity
import me.kofesst.lovemood.database.entities.RelationshipEntity

@Database(
    entities = [
        ProfileEntity::class,
        RelationshipEntity::class,
        PhotoMemoryEntity::class
    ],
    version = DatabaseConstants.VERSION
)
internal abstract class AppDatabase : RoomDatabase() {
    abstract fun getProfilesDao(): ProfilesDao
    abstract fun getRelationshipsDao(): RelationshipsDao
    abstract fun getPhotoMemoriesDao(): PhotoMemoriesDao
}