package com.denisyordanp.truckticketapp.test_util

import com.denisyordanp.truckticketapp.schema.entity.TicketEntity
import com.denisyordanp.truckticketapp.schema.remote.TicketRemote
import com.denisyordanp.truckticketapp.schema.ui.Ticket
import kotlin.random.Random

object DummyData {
    fun createTicketEntity(
        id: Long? = null,
        driver: String? = null,
        licence: String? = null,
        dateTime: Long? = null,
    ) = TicketEntity(
        id = id ?: Random.nextLong(),
        licence = licence ?: Random.nextString(10),
        driver = driver ?: Random.nextString(10),
        inbound = Random.nextLong(),
        outbound = Random.nextLong(),
        dateTime = dateTime ?: Random.nextLong()
    )

    fun createTicketRemote(id: Long? = null) = TicketRemote(
        id = id ?: Random.nextLong(),
        licence = Random.nextString(10),
        driver = Random.nextString(10),
        inbound = Random.nextLong(),
        outbound = Random.nextLong(),
        dateTime = Random.nextLong()
    )

    fun createTicket(id: Long? = null) = Ticket(
        id = id ?: Random.nextLong(),
        licence = Random.nextString(10),
        driver = Random.nextString(10),
        inbound = Random.nextLong(),
        outbound = Random.nextLong(),
        dateTime = Random.nextLong(),
        netWeight = Random.nextLong(),
    )
}