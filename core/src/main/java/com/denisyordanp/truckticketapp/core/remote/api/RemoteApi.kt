package com.denisyordanp.truckticketapp.core.remote.api

import com.denisyordanp.truckticketapp.schema.remote.TicketRemote

interface RemoteApi {
    suspend fun writeTicket(ticket: TicketRemote)
    suspend fun readTickets(): List<TicketRemote>
    suspend fun readTicket(id: Long): TicketRemote?
}