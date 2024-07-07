package com.denisyordanp.truckticketapp.domain.implementation

import com.denisyordanp.truckticketapp.common.di.DefaultDispatcher
import com.denisyordanp.truckticketapp.common.util.TicketParam
import com.denisyordanp.truckticketapp.data.api.LocalDataRepository
import com.denisyordanp.truckticketapp.domain.api.GetTruckTickets
import com.denisyordanp.truckticketapp.domain.helper.filterByParam
import com.denisyordanp.truckticketapp.domain.helper.getSorted
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetTruckTicketsImpl @Inject constructor(
    private val repository: LocalDataRepository,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher
) : GetTruckTickets {
    override fun invoke(sort: TicketParam, filter: Pair<TicketParam, String>?) =
        repository.getTickets().map { tickets ->
            tickets.map { it.toTicket() }
                .sortedBy { it.getSorted(sort) }
                .filterByParam(filter)
        }.flowOn(dispatcher)
}