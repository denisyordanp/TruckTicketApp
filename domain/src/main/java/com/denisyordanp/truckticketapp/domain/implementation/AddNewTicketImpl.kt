package com.denisyordanp.truckticketapp.domain.implementation

import com.denisyordanp.truckticketapp.common.di.IoDispatcher
import com.denisyordanp.truckticketapp.data.api.LocalDataRepository
import com.denisyordanp.truckticketapp.domain.api.AddNewTicket
import com.denisyordanp.truckticketapp.schema.ui.Ticket
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AddNewTicketImpl @Inject constructor(
    private val repository: LocalDataRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : AddNewTicket {
    override suspend fun invoke(ticket: Ticket) = withContext(dispatcher) {
        repository.insertTicket(ticket.toTicketEntity())
    }
}