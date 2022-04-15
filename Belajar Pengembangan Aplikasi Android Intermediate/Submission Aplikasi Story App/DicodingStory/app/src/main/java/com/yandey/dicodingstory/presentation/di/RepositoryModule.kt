package com.yandey.dicodingstory.presentation.di

import com.yandey.dicodingstory.data.repository.RepositoryImpl
import com.yandey.dicodingstory.data.repository.datasource.RemoteDataSource
import com.yandey.dicodingstory.domain.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Singleton
    @Provides
    fun provideRepository(
        remoteDataSource: RemoteDataSource
    ): Repository = RepositoryImpl(remoteDataSource)
}