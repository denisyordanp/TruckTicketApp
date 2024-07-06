package com.denisyordanp.truckticketapp.schema.entity

import com.denisyordanp.truckticketapp.common.extension.anyToLong
import junit.framework.TestCase.assertEquals
import org.junit.Test

class TicketEntityTest {

    @Test
    fun testToTicket() {
        val ticketEntity = TicketEntity(
            id = 1L,
            licence = "ABC123",
            driver = "John Doe",
            inbound = 123456789L,
            outbound = 987654321L,
            dateTime = 1234567890L
        )

        val ticket = ticketEntity.toTicket()

        assertEquals(ticketEntity.id.anyToLong(), ticket.id)
        assertEquals(ticketEntity.licence, ticket.licence)
        assertEquals(ticketEntity.driver, ticket.driver)
        assertEquals(ticketEntity.inbound.anyToLong(), ticket.inbound)
        assertEquals(ticketEntity.outbound.anyToLong(), ticket.outbound)
        assertEquals(ticketEntity.dateTime.anyToLong(), ticket.dateTime)
    }
}