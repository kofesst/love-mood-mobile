package me.kofesst.lovemood.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.kofesst.lovemood.core.usecases.models.PhotoMemoryUseCases
import me.kofesst.lovemood.core.usecases.models.ProfileUseCases
import me.kofesst.lovemood.core.usecases.models.RelationshipEventUseCases
import me.kofesst.lovemood.core.usecases.models.RelationshipUseCases
import me.kofesst.lovemood.database.AppDatabase
import me.kofesst.lovemood.database.impl.usecases.PhotoMemoryUseCasesImpl
import me.kofesst.lovemood.database.impl.usecases.ProfileUseCasesImpl
import me.kofesst.lovemood.database.impl.usecases.RelationshipEventUseCasesImpl
import me.kofesst.lovemood.database.impl.usecases.RelationshipUseCasesImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDatabaseModule {
    @Provides
    @Singleton
    fun provideProfileUseCases(
        database: AppDatabase
    ): ProfileUseCases {
        return ProfileUseCasesImpl(database.getProfilesDao())
    }

    @Provides
    @Singleton
    fun provideRelationshipUseCases(
        database: AppDatabase
    ): RelationshipUseCases {
        return RelationshipUseCasesImpl(
            database.getProfilesDao(),
            database.getRelationshipsDao()
        )
    }

    @Provides
    @Singleton
    fun provideRelationshipEventUseCases(
        database: AppDatabase
    ): RelationshipEventUseCases {
        return RelationshipEventUseCasesImpl(
            database.getProfilesDao(),
            database.getRelationshipsDao(),
            database.getRelationshipEventsDao()
        )
    }

    @Provides
    @Singleton
    fun providePhotoMemoryUseCases(
        database: AppDatabase
    ): PhotoMemoryUseCases {
        return PhotoMemoryUseCasesImpl(database.getPhotoMemoriesDao())
    }
}