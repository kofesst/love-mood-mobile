package me.kofesst.lovemood.database.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import me.kofesst.lovemood.database.AppDatabase
import me.kofesst.lovemood.database.DatabaseConstants
import javax.inject.Singleton

/**
 * DI модуль базы данных.
 */
@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room
            .databaseBuilder(appContext, AppDatabase::class.java, DatabaseConstants.FILENAME)
            .fallbackToDestructiveMigration()
            .build()
    }
}