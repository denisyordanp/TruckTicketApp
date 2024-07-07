package com.denisyordanp.truckticketapp.domain.api

import com.denisyordanp.truckticketapp.schema.ui.Ticket

fun interface UpdateTicket {
    suspend operator fun invoke(ticket: Ticket)
}