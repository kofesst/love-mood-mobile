package me.kofesst.lovemood.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import me.kofesst.lovemood.features.date.DateTimePattern
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FeaturesModule {
    @Provides
    @Singleton
    fun provideDateTimePattern(@ApplicationContext context: Context): DateTimePattern {
        return DateTimePattern.ofContext(context)
    }
}