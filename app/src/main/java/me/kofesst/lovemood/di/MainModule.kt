package me.kofesst.lovemood.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import me.kofesst.lovemood.core.usecases.AppUseCases
import me.kofesst.lovemood.core.usecases.datastore.DataStoreUseCases
import me.kofesst.lovemood.core.usecases.models.PhotoMemoryUseCases
import me.kofesst.lovemood.core.usecases.models.ProfileUseCases
import me.kofesst.lovemood.core.usecases.models.RelationshipEventUseCases
import me.kofesst.lovemood.core.usecases.models.RelationshipUseCases
import me.kofesst.lovemood.localization.dictionary.AppDictionary
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainModule {
    @Provides
    @Singleton
    fun provideAppDictionary(
        @ApplicationContext context: Context
    ): AppDictionary {
        return AppDictionary(context)
    }

    @Provides
    @Singleton
    fun provideAppUseCases(
        profileUseCases: ProfileUseCases,
        relationshipUseCases: RelationshipUseCases,
        relationshipEventUseCases: RelationshipEventUseCases,
        photoMemoryUseCases: PhotoMemoryUseCases,
        dataStoreUseCases: DataStoreUseCases
    ): AppUseCases {
        return AppUseCases(
            profile = profileUseCases,
            relationship = relationshipUseCases,
            relationshipEvents = relationshipEventUseCases,
            memories = photoMemoryUseCases,
            dataStore = dataStoreUseCases
        )
    }
}