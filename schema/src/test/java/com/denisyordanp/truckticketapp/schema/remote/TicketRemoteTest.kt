package com.denisyordanp.truckticketapp.schema.remote

import com.denisyordanp.truckticketapp.common.extension.anyToLong
import com.denisyordanp.truckticketapp.test_util.DummyData
import junit.framework.TestCase.assertEquals
import org.junit.Test

class TicketRemoteTest {

    @Test
    fun testToTicketEntity() {
        val ticketRemote = DummyData.createTicketRemote()

        val ticketEntity = ticketRemote.toTicketEntity()

        assertEquals(ticketRemote.id!!.anyToLong(), ticketEntity.id)
        assertEquals(ticketRemote.licence!!.toString(), ticketEntity.licence)
        assertEquals(ticketRemote.driver!!.toString(), ticketEntity.driver)
        assertEquals(ticketRemote.inbound!!.anyToLong(), ticketEntity.inbound)
        assertEquals(ticketRemote.outbound!!.anyToLong(), ticketEntity.outbound)
        assertEquals(ticketRemote.dateTime!!.anyToLong(), ticketEntity.dateTime)
    }

    @Test
    fun testToHashMap() {
        val ticketRemote = DummyData.createTicketRemote()

        val hashMap = ticketRemote.toHasMap()

        assertEquals(ticketRemote.id, hashMap["id"])
        assertEquals(ticketRemote.licence.toString(), hashMap["licence"])
        assertEquals(ticketRemote.driver.toString(), hashMap["driver"])
        assertEquals(ticketRemote.inbound, hashMap["inbound"])
        assertEquals(ticketRemote.outbound, hashMap["outbound"])
        assertEquals(ticketRemote.dateTime, hashMap["dateTime"])
    }
}