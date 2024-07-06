package com.denisyordanp.truckticketapp.data.di

import com.denisyordanp.truckticketapp.data.api.LocalDataRepository
import com.denisyordanp.truckticketapp.data.api.RemoteRepository
import com.denisyordanp.truckticketapp.data.implementation.LocalDataRepositoryImpl
import com.denisyordanp.truckticketapp.data.implementation.RemoteRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindLocalDataRepository(
        localDataRepositoryImpl: LocalDataRepositoryImpl
    ): LocalDataRepository

    @Binds
    abstract fun bindRemoteApiRepository(
        remoteRepositoryImpl: RemoteRepositoryImpl
    ): RemoteRepository
}