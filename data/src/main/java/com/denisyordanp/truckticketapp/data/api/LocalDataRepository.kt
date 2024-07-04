package com.denisyordanp.truckticketapp.data.api

import com.denisyordanp.truckticketapp.schema.entity.TicketEntity
import kotlinx.coroutines.flow.Flow

interface LocalDataRepository {
    fun getTickets(): Flow<List<TicketEntity>>
    suspend fun insertTicket(ticket: TicketEntity)
    suspend fun updateTicket(ticket: TicketEntity)
}