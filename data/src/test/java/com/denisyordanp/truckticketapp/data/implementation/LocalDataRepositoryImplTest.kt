package com.denisyordanp.truckticketapp.data.implementation

import com.denisyordanp.truckticketapp.core.database.dao.TicketsDao
import com.denisyordanp.truckticketapp.test_util.DummyData
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.random.Random


class LocalDataRepositoryImplTest {
    @Mock
    private lateinit var dao: TicketsDao

    private lateinit var localDataRepository: LocalDataRepositoryImpl

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        localDataRepository = LocalDataRepositoryImpl(dao, testDispatcher)
    }

    @Test
    fun `getTickets returns flow of tickets`() = runTest(testDispatcher) {
        // Given
        val tickets = listOf(
            DummyData.createTicketEntity(),
            DummyData.createTicketEntity(),
            DummyData.createTicketEntity(),
        )

        whenever(dao.getTickets()).thenReturn(flowOf(tickets))

        // When
        val result = localDataRepository.getTickets().first()

        // Then
        verify(dao).getTickets()
        assertEquals(tickets, result)
    }

    @Test
    fun `getTicketDetail returns flow of ticket detail`() = runTest(testDispatcher) {
        // Given
        val ticketId = Random.nextLong()
        val ticket = DummyData.createTicketEntity(id = ticketId)

        whenever(dao.getTicketDetail(ticketId)).thenReturn(flowOf(ticket))

        // When
        val result = localDataRepository.getTicketDetail(ticketId).first()

        // Then
        verify(dao).getTicketDetail(ticketId)
        assertEquals(ticket, result)
    }

    @Test
    fun `insertTicket calls dao upsertTicket`() = runTest(testDispatcher) {
        // Given
        val ticket = DummyData.createTicketEntity()

        // When
        localDataRepository.insertTicket(ticket)

        // Then
        verify(dao).upsertTicket(ticket)
    }

    @Test
    fun `insertTickets calls dao upsertTickets`() = runTest(testDispatcher) {
        // Given
        val tickets = listOf(
            DummyData.createTicketEntity(),
            DummyData.createTicketEntity(),
            DummyData.createTicketEntity()
        )

        // When
        localDataRepository.insertTickets(tickets)

        // Then
        verify(dao).upsertTickets(tickets)
    }

    @Test
    fun `updateTicket calls dao upsertTicket`() = runTest(testDispatcher) {
        // Given
        val ticket = DummyData.createTicketEntity()

        // When
        localDataRepository.updateTicket(ticket)

        // Then
        verify(dao).upsertTicket(ticket)
    }
}