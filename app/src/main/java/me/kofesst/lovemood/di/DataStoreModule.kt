package me.kofesst.lovemood.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import me.kofesst.lovemood.core.usecases.datastore.DataStoreUseCases
import me.kofesst.lovemood.core.usecases.datastore.GetSettingsUseCase
import me.kofesst.lovemood.core.usecases.datastore.SaveSettingsUseCase
import me.kofesst.lovemood.datastore.DatastoreConstants
import me.kofesst.lovemood.datastore.GetSettingsUseCaseImpl
import me.kofesst.lovemood.datastore.SaveSettingsUseCaseImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
    private val Context.dataStore by preferencesDataStore(name = DatastoreConstants.FILENAME)

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.dataStore
    }

    @Provides
    @Singleton
    fun provideSaveSettingsUseCase(dataStore: DataStore<Preferences>): SaveSettingsUseCase {
        return SaveSettingsUseCaseImpl(dataStore)
    }

    @Provides
    @Singleton
    fun provideGetSettingsUseCase(dataStore: DataStore<Preferences>): GetSettingsUseCase {
        return GetSettingsUseCaseImpl(dataStore)
    }

    @Provides
    @Singleton
    fun provideDataStoreUseCases(
        saveSettingsUseCase: SaveSettingsUseCase,
        getSettingsUseCase: GetSettingsUseCase
    ): DataStoreUseCases {
        return object : DataStoreUseCases {
            override val saveSettings: SaveSettingsUseCase = saveSettingsUseCase
            override val getSettings: GetSettingsUseCase = getSettingsUseCase
        }
    }
}