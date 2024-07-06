package com.denisyordanp.truckticketapp.domain.implementation

import com.denisyordanp.truckticketapp.common.di.IoDispatcher
import com.denisyordanp.truckticketapp.data.api.LocalDataRepository
import com.denisyordanp.truckticketapp.data.api.RemoteRepository
import com.denisyordanp.truckticketapp.domain.api.FetchTicketDetail
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FetchTicketDetailImpl @Inject constructor(
    private val remoteRepository: RemoteRepository,
    private val localRepository: LocalDataRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : FetchTicketDetail {
    override suspend fun invoke(id: Long) = withContext(dispatcher) {
        val response = remoteRepository.fetchTicketDetail(id)
        if (response != null) {
            localRepository.insertTicket(response.toTicketEntity())
        }
    }
}