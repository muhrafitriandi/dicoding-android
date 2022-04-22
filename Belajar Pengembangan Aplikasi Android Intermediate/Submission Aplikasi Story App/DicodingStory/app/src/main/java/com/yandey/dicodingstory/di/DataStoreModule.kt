package com.yandey.dicodingstory.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.yandey.dicodingstory.data.local.AuthDataStore
import com.yandey.dicodingstory.data.local.SettingsDataStore
import com.yandey.dicodingstory.util.Constant.DS_PREF
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private val Context.dsPref: DataStore<Preferences> by preferencesDataStore(name = DS_PREF)

@Module
@InstallIn(SingletonComponent::class)
class DataStoreModule {
    @Singleton
    @Provides
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> =
        context.dsPref

    @Singleton
    @Provides
    fun provideAuthDataStore(dataStore: DataStore<Preferences>): AuthDataStore =
        AuthDataStore(dataStore)

    @Singleton
    @Provides
    fun provideSettingsDataStore(dataStore: DataStore<Preferences>): SettingsDataStore =
        SettingsDataStore(dataStore)
}