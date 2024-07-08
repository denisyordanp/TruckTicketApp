package com.denisyordanp.truckticketapp.domain.implementation

import com.denisyordanp.truckticketapp.data.api.LocalDataRepository
import com.denisyordanp.truckticketapp.schema.entity.TicketEntity
import com.denisyordanp.truckticketapp.schema.ui.Ticket
import com.denisyordanp.truckticketapp.test_util.DummyData
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
        val ticketEntity = DummyData.createTicketEntity(ticketId)
        val ticket = ticketEntity.toTicket()

        whenever(localRepository.getTicketDetail(ticketId)).thenReturn(flowOf(ticketEntity))

        // When
        val result = getTicketDetail.invoke(ticketId).first()

        // Then
        assertEquals(ticket, result)
    }
}