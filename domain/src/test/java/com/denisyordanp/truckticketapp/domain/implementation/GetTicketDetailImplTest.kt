package com.denisyordanp.truckticketapp.domain.implementation

import com.denisyordanp.truckticketapp.data.api.LocalDataRepository
import com.denisyordanp.truckticketapp.schema.entity.TicketEntity
import com.denisyordanp.truckticketapp.schema.ui.Ticket
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever
import kotlin.random.Random

class GetTicketDetailImplTest {
    @Mock
    private lateinit var localRepository: LocalDataRepository

    private lateinit var getTicketDetail: GetTicketDetailImpl

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        getTicketDetail = GetTicketDetailImpl(localRepository, testDispatcher)
    }

    @Test
    fun `invoke returns transformed ticket detail`() = runTest(testDispatcher) {
        // Given
        val ticketId = Random.nextLong()
        val ticketEntity = TicketEntity(id = ticketId, "", "", 0, 0, 0)
        val ticket = Ticket(id = ticketId, "", "", 0, 0, 0, 0)

        whenever(localRepository.getTicketDetail(ticketId)).thenReturn(flowOf(ticketEntity))

        // When
        val result = getTicketDetail.invoke(ticketId).toList()

        // Then
        assertEquals(listOf(ticket), result)
    }
}