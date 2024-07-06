package com.denisyordanp.truckticketapp.schema.ui

import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import org.junit.Test

class TicketTest {

    @Test
    fun testToTicketEntity() {
        val ticket = Ticket(
            id = 1L,
            licence = "ABC123",
            driver = "John Doe",
            inbound = 123456789L,
            outbound = 987654321L,
            dateTime = 1234567890L,
            netWeight = null
        )

        val ticketEntity = ticket.toTicketEntity()

        assertEquals(ticket.id, ticketEntity.id)
        assertEquals(ticket.licence, ticketEntity.licence)
        assertEquals(ticket.driver, ticketEntity.driver)
        assertEquals(ticket.inbound, ticketEntity.inbound)
        assertEquals(ticket.outbound, ticketEntity.outbound)
        assertEquals(ticket.dateTime, ticketEntity.dateTime)
    }

    @Test
    fun testToTicketRemote() {
        val ticket = Ticket(
            id = 1L,
            licence = "ABC123",
            driver = "John Doe",
            inbound = 123456789L,
            outbound = 987654321L,
            dateTime = 1234567890L,
            netWeight = null
        )

        val id = 10L
        val ticketRemote = ticket.toTicketRemote(id)

        assertEquals(id, ticketRemote.id)
        assertEquals(ticket.licence, ticketRemote.licence)
        assertEquals(ticket.driver, ticketRemote.driver)
        assertEquals(ticket.inbound, ticketRemote.inbound)
        assertEquals(ticket.outbound, ticketRemote.outbound)
        assertEquals(ticket.dateTime, ticketRemote.dateTime)
    }

    @Test
    fun testNewTicket() {
        val licence = "DEF456"
        val driver = "Jane Doe"
        val inbound = 987654321L
        val dateTime = 0L

        val newTicket = Ticket.newTicket(licence, driver, inbound, dateTime)

        assertEquals(0L, newTicket.id)
        assertEquals(licence, newTicket.licence)
        assertEquals(driver, newTicket.driver)
        assertEquals(inbound, newTicket.inbound)
        assertNull(newTicket.outbound)
        assertEquals(dateTime, newTicket.dateTime)
        assertNull(newTicket.netWeight)
    }

    @Test
    fun testExistingTicket() {
        val id = 10L
        val licence = "DEF456"
        val driver = "Jane Doe"
        val inbound = 987654321L
        val outbound = 123456789L
        val dateTime = 0L

        val existingTicket = Ticket.existingTicket(id, licence, driver, inbound, outbound, dateTime)

        assertEquals(id, existingTicket.id)
        assertEquals(licence, existingTicket.licence)
        assertEquals(driver, existingTicket.driver)
        assertEquals(inbound, existingTicket.inbound)
        assertEquals(outbound, existingTicket.outbound)
        assertEquals(dateTime, existingTicket.dateTime)
        assertNull(existingTicket.netWeight)
    }
}