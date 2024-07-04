package com.denisyordanp.truckticketapp.domain.api

import com.denisyordanp.truckticketapp.schema.ui.Ticket

interface AddNewTicket {
    suspend operator fun invoke(ticket: Ticket)
}