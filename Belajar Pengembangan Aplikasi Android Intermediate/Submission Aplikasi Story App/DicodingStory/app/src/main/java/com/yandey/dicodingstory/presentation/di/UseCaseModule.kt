package com.yandey.dicodingstory.presentation.di

import com.yandey.dicodingstory.domain.repository.Repository
import com.yandey.dicodingstory.domain.usecase.LoginUseCase
import com.yandey.dicodingstory.domain.usecase.RegisterUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {
    @Singleton
    @Provides
    fun provideLoginUseCase(repository: Repository): LoginUseCase = LoginUseCase(repository)

    @Singleton
    @Provides
    fun provideRegisterUseCase(repository: Repository): RegisterUseCase = RegisterUseCase(repository)
}