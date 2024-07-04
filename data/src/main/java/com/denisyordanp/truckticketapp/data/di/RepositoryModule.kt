package com.denisyordanp.truckticketapp.data.di

import com.denisyordanp.truckticketapp.data.api.LocalDataRepository
import com.denisyordanp.truckticketapp.data.implementation.LocalDataRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindLocalDataRepository(
        localDataRepository: LocalDataRepositoryImpl
    ): LocalDataRepository
}