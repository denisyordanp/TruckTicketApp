package com.denisyordanp.truckticketapp.core.di

import android.content.Context
import com.denisyordanp.truckticketapp.core.database.AppDatabase
import com.denisyordanp.truckticketapp.core.database.dao.TicketsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase = AppDatabase.getInstance(context)

    @Provides
    fun provideTicketDao(appDatabase: AppDatabase): TicketsDao = appDatabase.ticketDao()
}