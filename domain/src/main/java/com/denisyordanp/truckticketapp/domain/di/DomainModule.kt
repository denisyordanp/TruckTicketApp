package com.denisyordanp.truckticketapp.domain.di

import com.denisyordanp.truckticketapp.domain.api.AddNewTicket
import com.denisyordanp.truckticketapp.domain.api.GetTruckTickets
import com.denisyordanp.truckticketapp.domain.implementation.AddNewTicketImpl
import com.denisyordanp.truckticketapp.domain.implementation.GetTruckTicketsImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DomainModule {
    @Binds
    abstract fun bindGetTruckTickets(
        getTruckTicketsImpl: GetTruckTicketsImpl
    ): GetTruckTickets

    @Binds
    abstract fun bindAddNewTicket(
        addNewTicketImpl: AddNewTicketImpl
    ): AddNewTicket
}