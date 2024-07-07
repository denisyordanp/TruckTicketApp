package com.denisyordanp.truckticketapp.domain.api

import com.denisyordanp.truckticketapp.common.util.TicketParam
import com.denisyordanp.truckticketapp.schema.ui.Ticket
import kotlinx.coroutines.flow.Flow

fun interface GetTruckTickets {
    operator fun invoke(sort: TicketParam, filter: Pair<TicketParam, String>?): Flow<List<Ticket>>
}