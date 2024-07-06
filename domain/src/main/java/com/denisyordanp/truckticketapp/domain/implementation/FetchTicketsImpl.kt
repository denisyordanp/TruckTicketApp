package com.denisyordanp.truckticketapp.domain.implementation

import com.denisyordanp.truckticketapp.common.di.IoDispatcher
import com.denisyordanp.truckticketapp.data.api.LocalDataRepository
import com.denisyordanp.truckticketapp.data.api.RemoteRepository
import com.denisyordanp.truckticketapp.domain.api.FetchTickets
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FetchTicketsImpl @Inject constructor(
    private val remoteRepository: RemoteRepository,
    private val localRepository: LocalDataRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : FetchTickets {
    override suspend fun invoke() = withContext(dispatcher) {
        val response = remoteRepository.fetchTickets()

        localRepository.insertTickets(response.map { it.toTicketEntity() })
    }
}