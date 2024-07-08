package com.denisyordanp.truckticketapp.domain.implementation

import com.denisyordanp.truckticketapp.common.util.TicketParam
import com.denisyordanp.truckticketapp.data.api.LocalDataRepository
import com.denisyordanp.truckticketapp.domain.helper.filterByParam
import com.denisyordanp.truckticketapp.domain.helper.getSorted
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
            DummyData.createTicketEntity(driver = "John Doe"),
            DummyData.createTicketEntity(driver = "John Doe"),
            DummyData.createTicketEntity(driver = "Salim")
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
            DummyData.createTicketEntity(driver = "John Doe"),
            DummyData.createTicketEntity(driver = "John Doe"),
            DummyData.createTicketEntity(driver = "Salim")
        )
        val expectedTickets = tickets.map { it.toTicket() }
            .sortedBy { it.getSorted(TicketParam.DRIVER) }
            .filterByParam(TicketParam.DRIVER to "John Doe")

        whenever(repository.getTickets()).thenReturn(flowOf(tickets))

        // When
        val result =
            getTruckTickets.invoke(TicketParam.DRIVER, TicketParam.DRIVER to "John Doe").first()

        // Then
        verify(repository).getTickets()
        assertEquals(expectedTickets, result)
    }

    @Test
    fun `invoke sorts and filters tickets by DATE`() = runTest(testDispatcher) {
        // Given
        val tickets = listOf(
            DummyData.createTicketEntity(dateTime = 1704042000000),
            DummyData.createTicketEntity(dateTime = 1704042000000),
            DummyData.createTicketEntity(dateTime = 0),
        )
        val expectedTickets = tickets.map { it.toTicket() }
            .sortedBy { it.getSorted(TicketParam.DATE) }
            .filterByParam(TicketParam.DATE to "01-01-2024")

        whenever(repository.getTickets()).thenReturn(flowOf(tickets))

        // When
        val result =
            getTruckTickets.invoke(TicketParam.DATE, TicketParam.DATE to "01-01-2024").first()

        // Then
        verify(repository).getTickets()
        assertEquals(expectedTickets, result)
    }

    @Test
    fun `invoke sorts and filters tickets by LICENSE`() = runTest(testDispatcher) {
        // Given
        val tickets = listOf(
            DummyData.createTicketEntity(licence = "AB12345L"),
            DummyData.createTicketEntity(licence = "AB12345L"),
            DummyData.createTicketEntity(licence = "CD32455R")
        )
        val expectedTickets = tickets.map { it.toTicket() }
            .sortedBy { it.getSorted(TicketParam.LICENSE) }
            .filterByParam(TicketParam.LICENSE to "AB12345L")

        whenever(repository.getTickets()).thenReturn(flowOf(tickets))

        // When
        val result =
            getTruckTickets.invoke(TicketParam.LICENSE, TicketParam.LICENSE to "AB12345L").first()

        // Then
        verify(repository).getTickets()
        assertEquals(expectedTickets, result)
    }
}