package com.denisyordanp.truckticketapp.schema.ui

import com.denisyordanp.truckticketapp.schema.entity.TicketEntity

data class Ticket(
    val id: Long,
    val licence: String,
    val driver: String,
    val inbound: Long,
    val outbound: Long?,
    val dateTime: Long,
    val netWeight: Long?
) {
    fun toTicketEntity() = TicketEntity(
        id = id,
        licence = licence,
        driver = driver,
        inbound = inbound,
        outbound = outbound,
        dateTime = dateTime
    )

    companion object {
        fun newTicket(
            licence: String,
            driver: String,
            inbound: Long,
            dateTime: Long
        ) = Ticket(
            id = 0L,
            licence = licence,
            driver = driver,
            inbound = inbound,
            outbound = null,
            dateTime = dateTime,
            netWeight = inbound
        )

        fun editTicket(
            id: Long,
            licence: String,
            driver: String,
            inbound: Long,
            outbound: Long,
            dateTime: Long
        ) = Ticket(
            id = id,
            licence = licence,
            driver = driver,
            inbound = inbound,
            outbound = outbound,
            dateTime = dateTime,
            netWeight = inbound
        )
    }
}
