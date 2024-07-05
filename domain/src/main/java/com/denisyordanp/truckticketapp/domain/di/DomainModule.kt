package com.denisyordanp.truckticketapp.domain.di

import com.denisyordanp.truckticketapp.domain.api.AddNewTicket
import com.denisyordanp.truckticketapp.domain.api.GetTicketDetail
import com.denisyordanp.truckticketapp.domain.api.GetTruckTickets
import com.denisyordanp.truckticketapp.domain.api.UpdateTicket
import com.denisyordanp.truckticketapp.domain.implementation.AddNewTicketImpl
import com.denisyordanp.truckticketapp.domain.implementation.GetTicketDetailImpl
import com.denisyordanp.truckticketapp.domain.implementation.GetTruckTicketsImpl
import com.denisyordanp.truckticketapp.domain.implementation.UpdateTicketImpl
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

    @Binds
    abstract fun bindGetTicketDetail(
        getTicketDetailImpl: GetTicketDetailImpl
    ): GetTicketDetail

    @Binds
    abstract fun bindUpdateTicket(
        updateTicketImpl: UpdateTicketImpl
    ): UpdateTicket
}