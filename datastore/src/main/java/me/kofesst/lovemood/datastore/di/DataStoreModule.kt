package me.kofesst.lovemood.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import me.kofesst.lovemood.core.repository.SessionRepository
import me.kofesst.lovemood.datastore.DatastoreConstants
import me.kofesst.lovemood.datastore.impl.SessionRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DataStoreModule {
    private val Context.appDataStore by preferencesDataStore(name = DatastoreConstants.FILENAME)

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext appContext: Context): DataStore<Preferences> {
        return appContext.appDataStore
    }

    @Provides
    @Singleton
    fun provideSessionRepository(dataStore: DataStore<Preferences>): SessionRepository {
        return SessionRepositoryImpl(dataStore)
    }
}