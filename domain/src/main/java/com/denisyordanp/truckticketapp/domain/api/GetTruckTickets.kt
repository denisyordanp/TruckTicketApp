package com.denisyordanp.truckticketapp.domain.api

import com.denisyordanp.truckticketapp.schema.ui.Ticket
import kotlinx.coroutines.flow.Flow

interface GetTruckTickets {
    operator fun invoke(): Flow<List<Ticket>>
}