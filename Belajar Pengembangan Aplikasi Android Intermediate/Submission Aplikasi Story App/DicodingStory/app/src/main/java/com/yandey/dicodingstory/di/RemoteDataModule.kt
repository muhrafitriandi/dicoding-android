package com.yandey.dicodingstory.di

import com.yandey.dicodingstory.data.remote.ApiConfig
import com.yandey.dicodingstory.data.remote.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteDataModule {
    @Singleton
    @Provides
    fun provideApiService(): ApiService = ApiConfig.getApiService()
}