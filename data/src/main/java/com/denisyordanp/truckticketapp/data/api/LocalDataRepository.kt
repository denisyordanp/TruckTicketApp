package com.denisyordanp.truckticketapp.data.api

import com.denisyordanp.truckticketapp.schema.entity.TicketEntity
import kotlinx.coroutines.flow.Flow

interface LocalDataRepository {
    fun getTickets(): Flow<List<TicketEntity>>
    fun getTicketDetail(id: Long): Flow<TicketEntity?>
    suspend fun insertTicket(ticket: TicketEntity): Long
    suspend fun insertTickets(tickets: List<TicketEntity>)
    suspend fun updateTicket(ticket: TicketEntity): Long
}