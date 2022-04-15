package com.yandey.dicodingstory.presentation.di

import android.app.Application
import com.yandey.dicodingstory.domain.usecase.LoginUseCase
import com.yandey.dicodingstory.domain.usecase.RegisterUseCase
import com.yandey.dicodingstory.presentation.viewmodel.UserViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FactoryModule {
    @Singleton
    @Provides
    fun provideUserViewModelFactory(
        app: Application,
        loginUseCase: LoginUseCase,
        registerUseCase: RegisterUseCase
    ): UserViewModelFactory = UserViewModelFactory(
        app,
        loginUseCase,
        registerUseCase
    )
}