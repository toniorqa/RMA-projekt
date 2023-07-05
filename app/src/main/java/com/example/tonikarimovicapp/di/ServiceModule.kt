package com.example.tonikarimovicapp.di

import com.example.tonikarimovicapp.services.AccountService
import com.example.tonikarimovicapp.services.AccountServiceImpl
import com.example.tonikarimovicapp.services.StorageService
import com.example.tonikarimovicapp.services.StorageServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class ServiceModule {
    @Binds
    abstract fun provideAccountService(impl: AccountServiceImpl): AccountService

    @Binds
    abstract fun provideStorageService(impl: StorageServiceImpl): StorageService
}