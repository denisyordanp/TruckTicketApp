package com.denisyordanp.truckticketapp.domain.api

import com.denisyordanp.truckticketapp.schema.ui.Ticket
import kotlinx.coroutines.flow.Flow

interface GetTicketDetail {
    operator fun invoke(id: Long): Flow<Ticket?>
}