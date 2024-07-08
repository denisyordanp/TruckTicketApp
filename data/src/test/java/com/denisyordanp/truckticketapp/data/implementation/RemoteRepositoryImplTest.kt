package com.denisyordanp.truckticketapp.data.implementation

import com.denisyordanp.truckticketapp.core.remote.api.RemoteApi
import com.denisyordanp.truckticketapp.test_util.DummyData
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.random.Random


class RemoteRepositoryImplTest {
    @Mock
    private lateinit var remote: RemoteApi

    private lateinit var remoteRepository: RemoteRepositoryImpl

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        remoteRepository = RemoteRepositoryImpl(remote, testDispatcher)
    }

    @Test
    fun `postTicket calls remote writeTicket`() = runTest(testDispatcher) {
        // Given
        val ticket = DummyData.createTicketRemote()

        // When
        remoteRepository.postTicket(ticket)

        // Then
        verify(remote).writeTicket(ticket)
    }

    @Test
    fun `fetchTickets returns list of tickets`() = runTest(testDispatcher) {
        // Given
        val tickets = listOf(
            DummyData.createTicketRemote(),
            DummyData.createTicketRemote(),
            DummyData.createTicketRemote(),
        )

        whenever(remote.readTickets()).thenReturn(tickets)

        // When
        val result = remoteRepository.fetchTickets()

        // Then
        verify(remote).readTickets()
        assertEquals(tickets, result)
    }

    @Test
    fun `fetchTicketDetail returns ticket detail`() = runTest(testDispatcher) {
        // Given
        val ticketId = Random.nextLong()
        val ticket = DummyData.createTicketRemote(id = ticketId)

        whenever(remote.readTicket(ticketId)).thenReturn(ticket)

        // When
        val result = remoteRepository.fetchTicketDetail(ticketId)

        // Then
        verify(remote).readTicket(ticketId)
        assertEquals(ticket, result)
    }
}