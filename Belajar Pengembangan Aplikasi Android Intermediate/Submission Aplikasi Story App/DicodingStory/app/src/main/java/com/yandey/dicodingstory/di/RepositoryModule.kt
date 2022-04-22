package com.yandey.dicodingstory.di

import com.yandey.dicodingstory.data.repository.auth.AuthRepository
import com.yandey.dicodingstory.data.repository.auth.AuthRepositoryImpl
import com.yandey.dicodingstory.data.repository.settings.SettingsRepository
import com.yandey.dicodingstory.data.repository.settings.SettingsRepositoryImpl
import com.yandey.dicodingstory.data.repository.stories.StoriesRepository
import com.yandey.dicodingstory.data.repository.stories.StoriesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindsAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    abstract fun bindsStoriesRepository(storiesRepositoryImpl: StoriesRepositoryImpl): StoriesRepository

    @Binds
    abstract fun bindsSettingsRepository(settingsRepositoryImpl: SettingsRepositoryImpl): SettingsRepository
}