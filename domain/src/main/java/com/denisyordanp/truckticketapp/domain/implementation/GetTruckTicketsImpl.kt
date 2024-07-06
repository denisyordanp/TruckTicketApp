package com.denisyordanp.truckticketapp.domain.implementation

import com.denisyordanp.truckticketapp.common.di.DefaultDispatcher
import com.denisyordanp.truckticketapp.common.extension.toFormattedDateString
import com.denisyordanp.truckticketapp.common.util.DateFormat
import com.denisyordanp.truckticketapp.common.util.TicketParam
import com.denisyordanp.truckticketapp.data.api.LocalDataRepository
import com.denisyordanp.truckticketapp.domain.api.GetTruckTickets
import com.denisyordanp.truckticketapp.schema.ui.Ticket
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

    private fun List<Ticket>.filterByParam(filter: Pair<TicketParam, String>?) =
        this.let { original ->
            filter?.let { f ->
                original.filter {
                    val value = when (f.first) {
                        TicketParam.DATE -> it.dateTime.toFormattedDateString(DateFormat.DAY_MONTH_YEAR)
                        TicketParam.DRIVER -> it.driver
                        TicketParam.LICENSE -> it.licence
                    }

                    value.equals(f.second, true)
                }
            } ?: original
        }

    private fun Ticket.getSorted(sort: TicketParam): String = when (sort) {
        TicketParam.DATE -> this.dateTime.toString()
        TicketParam.DRIVER -> this.driver
        TicketParam.LICENSE -> this.licence
    }
}