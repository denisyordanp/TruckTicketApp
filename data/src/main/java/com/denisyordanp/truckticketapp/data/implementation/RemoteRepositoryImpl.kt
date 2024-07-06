package com.denisyordanp.truckticketapp.data.implementation

import com.denisyordanp.truckticketapp.common.di.IoDispatcher
import com.denisyordanp.truckticketapp.core.remote.api.RemoteApi
import com.denisyordanp.truckticketapp.data.api.RemoteRepository
import com.denisyordanp.truckticketapp.schema.remote.TicketRemote
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoteRepositoryImpl @Inject constructor(
    private val remote: RemoteApi,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : RemoteRepository {
    override suspend fun postTicket(ticket: TicketRemote) = withContext(dispatcher) {
        remote.writeTicket(ticket)
    }

    override suspend fun fetchTickets() = withContext(dispatcher) {
        remote.readTickets()
    }

    override suspend fun fetchTicketDetail(id: Long) = withContext(dispatcher) {
        remote.readTicket(id)
    }
}