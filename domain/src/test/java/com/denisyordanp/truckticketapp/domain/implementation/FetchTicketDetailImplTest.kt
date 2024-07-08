package com.denisyordanp.truckticketapp.domain.implementation

import com.denisyordanp.truckticketapp.data.api.LocalDataRepository
import com.denisyordanp.truckticketapp.data.api.RemoteRepository
import com.denisyordanp.truckticketapp.test_util.DummyData
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.random.Random

class FetchTicketDetailImplTest {
    @Mock
    private lateinit var remoteRepository: RemoteRepository

    @Mock
    private lateinit var localRepository: LocalDataRepository

    private lateinit var fetchTicketDetail: FetchTicketDetailImpl

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        fetchTicketDetail = FetchTicketDetailImpl(remoteRepository, localRepository, testDispatcher)
    }

    @Test
    fun `invoke fetches ticket detail from remote and inserts into local repository`() =
        runTest(testDispatcher) {
            // Given
            val ticketId = Random.nextLong()
            val ticketRemote = DummyData.createTicketRemote(ticketId)
            val ticketEntity = ticketRemote.toTicketEntity()

            whenever(remoteRepository.fetchTicketDetail(ticketId)).thenReturn(ticketRemote)

            // When
            fetchTicketDetail.invoke(ticketId)

            // Then
            verify(remoteRepository).fetchTicketDetail(ticketId)
            verify(localRepository).insertTicket(ticketEntity)
        }

    @Test
    fun `invoke does not insert null ticket detail into local repository`() =
        runTest(testDispatcher) {
            // Given
            val ticketId = Random.nextLong()

            whenever(remoteRepository.fetchTicketDetail(ticketId)).thenReturn(null)

            // When
            fetchTicketDetail.invoke(ticketId)

            // Then
            verify(remoteRepository).fetchTicketDetail(ticketId)
            verify(localRepository, never()).insertTicket(any())
        }
}