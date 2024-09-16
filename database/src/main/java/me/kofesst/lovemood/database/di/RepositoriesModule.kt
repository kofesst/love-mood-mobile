package me.kofesst.lovemood.database.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.kofesst.lovemood.core.repository.MemoriesRepository
import me.kofesst.lovemood.core.repository.ProfileRepository
import me.kofesst.lovemood.core.repository.RelationshipRepository
import me.kofesst.lovemood.database.AppDatabase
import me.kofesst.lovemood.database.impl.repository.MemoryRepositoryImpl
import me.kofesst.lovemood.database.impl.repository.ProfileRepositoryImpl
import me.kofesst.lovemood.database.impl.repository.RelationshipRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object RepositoriesModule {
    @Provides
    @Singleton
    fun provideProfileRepository(database: AppDatabase): ProfileRepository {
        return ProfileRepositoryImpl(database)
    }

    @Provides
    @Singleton
    fun provideRelationshipRepository(database: AppDatabase): RelationshipRepository {
        return RelationshipRepositoryImpl(database)
    }

    @Provides
    @Singleton
    fun provideMemoryRepository(database: AppDatabase): MemoriesRepository {
        return MemoryRepositoryImpl(database)
    }
}