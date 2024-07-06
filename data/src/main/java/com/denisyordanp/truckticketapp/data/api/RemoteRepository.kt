package com.denisyordanp.truckticketapp.data.api

import com.denisyordanp.truckticketapp.schema.remote.TicketRemote

interface RemoteRepository {
    suspend fun postTicket(ticket: TicketRemote)
    suspend fun fetchTickets(): List<TicketRemote>
    suspend fun fetchTicketDetail(id: Long): TicketRemote?
}