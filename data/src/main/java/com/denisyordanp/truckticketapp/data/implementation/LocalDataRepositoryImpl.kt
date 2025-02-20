package com.denisyordanp.truckticketapp.data.implementation

import com.denisyordanp.truckticketapp.common.di.DefaultDispatcher
import com.denisyordanp.truckticketapp.core.database.dao.TicketsDao
import com.denisyordanp.truckticketapp.data.api.LocalDataRepository
import com.denisyordanp.truckticketapp.schema.entity.TicketEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalDataRepositoryImpl @Inject constructor(
    private val dao: TicketsDao,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher
) : LocalDataRepository {
    override fun getTickets() = dao.getTickets().flowOn(dispatcher)
    override fun getTicketDetail(id: Long) = dao.getTicketDetail(id).flowOn(dispatcher)

    override suspend fun insertTicket(ticket: TicketEntity) = withContext(dispatcher) {
        dao.upsertTicket(ticket)
    }

    override suspend fun insertTickets(tickets: List<TicketEntity>) {
        dao.upsertTickets(tickets)
    }

    override suspend fun updateTicket(ticket: TicketEntity) = withContext(dispatcher) {
        dao.upsertTicket(ticket)
    }
}