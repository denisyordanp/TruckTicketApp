package com.denisyordanp.truckticketapp.domain.implementation

import com.denisyordanp.truckticketapp.common.util.TicketParam
import com.denisyordanp.truckticketapp.data.api.LocalDataRepository
import com.denisyordanp.truckticketapp.domain.helper.filterByParam
import com.denisyordanp.truckticketapp.domain.helper.getSorted
import com.denisyordanp.truckticketapp.schema.entity.TicketEntity
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.random.Random

class GetTruckTicketsImplTest {
    @Mock
    private lateinit var repository: LocalDataRepository

    private lateinit var getTruckTickets: GetTruckTicketsImpl

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        getTruckTickets = GetTruckTicketsImpl(repository, testDispatcher)
    }

    @Test
    fun `invoke when param null`() = runTest(testDispatcher) {
        // Given
        val tickets = listOf(
            TicketEntity(id = Random.nextLong(), "", "John Doe", 0, 0, 0),
            TicketEntity(id = Random.nextLong(), "", "John Doe", 0, 0, 0),
            TicketEntity(id = Random.nextLong(), "", "Salim", 0, 0, 0)
        )
        val expectedTickets = tickets.map { it.toTicket() }

        whenever(repository.getTickets()).thenReturn(flowOf(tickets))

        // When
        val result = getTruckTickets.invoke(TicketParam.DRIVER, null).first()

        // Then
        verify(repository).getTickets()
        assertEquals(expectedTickets, result)
    }

    @Test
    fun `invoke sorts and filters tickets by DRIVER`() = runTest(testDispatcher) {
        // Given
        val tickets = listOf(
            TicketEntity(id = Random.nextLong(), "", "John Doe", 0, 0, 0),
            TicketEntity(id = Random.nextLong(), "", "John Doe", 0, 0, 0),
            TicketEntity(id = Random.nextLong(), "", "Salim", 0, 0, 0)
        )
        val expectedTickets = tickets.map { it.toTicket() }
            .sortedBy { it.getSorted(TicketParam.DRIVER) }
            .filterByParam(TicketParam.DRIVER to "John Doe")

        whenever(repository.getTickets()).thenReturn(flowOf(tickets))

        // When
        val result = getTruckTickets.invoke(TicketParam.DRIVER, TicketParam.DRIVER to "John Doe").first()

        // Then
        verify(repository).getTickets()
        assertEquals(expectedTickets, result)
    }

    @Test
    fun `invoke sorts and filters tickets by DATE`() = runTest(testDispatcher) {
        // Given
        val tickets = listOf(
            TicketEntity(id = Random.nextLong(), "", "", 0, 0, 1704042000000),
            TicketEntity(id = Random.nextLong(), "", "", 0, 0, 1704042000000),
            TicketEntity(id = Random.nextLong(), "", "", 0, 0, 0)
        )
        val expectedTickets = tickets.map { it.toTicket() }
            .sortedBy { it.getSorted(TicketParam.DATE) }
            .filterByParam(TicketParam.DATE to "01-01-2024")

        whenever(repository.getTickets()).thenReturn(flowOf(tickets))

        // When
        val result = getTruckTickets.invoke(TicketParam.DATE, TicketParam.DATE to "01-01-2024").first()

        // Then
        verify(repository).getTickets()
        assertEquals(expectedTickets, result)
    }

    @Test
    fun `invoke sorts and filters tickets by LICENSE`() = runTest(testDispatcher) {
        // Given
        val tickets = listOf(
            TicketEntity(id = Random.nextLong(), "ABC123", "", 0, 0, 0),
            TicketEntity(id = Random.nextLong(), "ABC123", "", 0, 0, 0),
            TicketEntity(id = Random.nextLong(), "123ABC", "", 0, 0, 0)
        )
        val expectedTickets = tickets.map { it.toTicket() }
            .sortedBy { it.getSorted(TicketParam.LICENSE) }
            .filterByParam(TicketParam.LICENSE to "ABC123")

        whenever(repository.getTickets()).thenReturn(flowOf(tickets))

        // When
        val result = getTruckTickets.invoke(TicketParam.LICENSE, TicketParam.LICENSE to "ABC123").first()

        // Then
        verify(repository).getTickets()
        assertEquals(expectedTickets, result)
    }
}