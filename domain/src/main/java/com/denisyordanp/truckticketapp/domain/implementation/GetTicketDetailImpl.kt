package com.denisyordanp.truckticketapp.domain.implementation

import com.denisyordanp.truckticketapp.common.di.IoDispatcher
import com.denisyordanp.truckticketapp.data.api.LocalDataRepository
import com.denisyordanp.truckticketapp.domain.api.GetTicketDetail
import com.denisyordanp.truckticketapp.schema.ui.Ticket
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetTicketDetailImpl @Inject constructor(
    private val repository: LocalDataRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : GetTicketDetail {
    override fun invoke(licence: String) = repository.getTicketDetail(licence).map {
        it?.mapToTicket()
    }.flowOn(dispatcher)
}