package com.denisyordanp.truckticketapp.domain.implementation

import com.denisyordanp.truckticketapp.common.extension.toFormattedDateString
import com.denisyordanp.truckticketapp.common.util.DateFormat
import com.denisyordanp.truckticketapp.common.util.TicketParam
import com.denisyordanp.truckticketapp.data.api.LocalDataRepository
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

class GetTicketFilterImplTest {
    @Mock
    private lateinit var repository: LocalDataRepository

    private lateinit var getTicketFilter: GetTicketFilterImpl

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        getTicketFilter = GetTicketFilterImpl(repository, testDispatcher)
    }

    @Test
    fun `invoke filters tickets by DATE`() = runTest(testDispatcher) {
        // Given
        val tickets = listOf(
            TicketEntity(id = Random.nextLong(), "", "John Doe", 0, 0, 0),
            TicketEntity(id = Random.nextLong(), "", "John Doe", 0, 0, 0),
            TicketEntity(id = Random.nextLong(), "", "Salim", 0, 0, 0)
        )
        val expectedDates = tickets.distinctBy {
            it.dateTime.toFormattedDateString(DateFormat.DAY_MONTH_YEAR)
        }.map { it.dateTime }.take(5)

        whenever(repository.getTickets()).thenReturn(flowOf(tickets))

        // When
        val result = getTicketFilter.invoke<String>(TicketParam.DATE).first()

        // Then
        verify(repository).getTickets()
        assertEquals(expectedDates, result)
    }

    @Test
    fun `invoke filters tickets by DRIVER`() = runTest(testDispatcher) {
        // Given
        val tickets = listOf(
            TicketEntity(id = Random.nextLong(), "", "John Doe", 0, 0, 0),
            TicketEntity(id = Random.nextLong(), "", "John Doe", 0, 0, 0),
            TicketEntity(id = Random.nextLong(), "", "Salim", 0, 0, 0)
        )
        val expectedDrivers = tickets.distinctBy { it.dateTime }.map { it.driver }.take(5)

        whenever(repository.getTickets()).thenReturn(flowOf(tickets))

        // When
        val result = getTicketFilter.invoke<String>(TicketParam.DRIVER).first()

        // Then
        verify(repository).getTickets()
        assertEquals(expectedDrivers, result)
    }

    @Test
    fun `invoke filters tickets by LICENSE`() = runTest(testDispatcher) {
        // Given
        val tickets = listOf(
            TicketEntity(id = Random.nextLong(), "", "John Doe", 0, 0, 0),
            TicketEntity(id = Random.nextLong(), "", "John Doe", 0, 0, 0),
            TicketEntity(id = Random.nextLong(), "", "Salim", 0, 0, 0)
        )
        val expectedLicenses = tickets.distinctBy { it.dateTime }.map { it.licence }.take(5)

        whenever(repository.getTickets()).thenReturn(flowOf(tickets))

        // When
        val result = getTicketFilter.invoke<String>(TicketParam.LICENSE).first()

        // Then
        verify(repository).getTickets()
        assertEquals(expectedLicenses, result)
    }
}