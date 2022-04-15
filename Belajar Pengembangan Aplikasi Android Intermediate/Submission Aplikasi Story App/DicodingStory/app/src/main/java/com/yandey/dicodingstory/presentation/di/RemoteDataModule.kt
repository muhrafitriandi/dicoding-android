package com.yandey.dicodingstory.presentation.di

import com.yandey.dicodingstory.data.remote.ApiService
import com.yandey.dicodingstory.data.repository.datasource.RemoteDataSource
import com.yandey.dicodingstory.data.repository.datasourceimpl.RemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RemoteDataModule {
    @Singleton
    @Provides
    fun provideRemoteDataSource(apiService: ApiService): RemoteDataSource = RemoteDataSourceImpl(apiService)
}