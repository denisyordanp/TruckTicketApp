package com.denisyordanp.truckticketapp.domain.api

import com.denisyordanp.truckticketapp.schema.ui.Ticket

interface UpdateTicket {
    suspend operator fun invoke(ticket: Ticket)
}