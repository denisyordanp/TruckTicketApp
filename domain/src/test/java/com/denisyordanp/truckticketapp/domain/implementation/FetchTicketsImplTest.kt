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
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class FetchTicketsImplTest {
    @Mock
    private lateinit var remoteRepository: RemoteRepository

    @Mock
    private lateinit var localRepository: LocalDataRepository

    private lateinit var fetchTickets: FetchTicketsImpl

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        fetchTickets = FetchTicketsImpl(remoteRepository, localRepository, testDispatcher)
    }

    @Test
    fun `invoke fetches tickets from remote and inserts into local repository`() =
        runTest(testDispatcher) {
            // Given
            val remoteTickets = listOf(
                DummyData.createTicketRemote(),
                DummyData.createTicketRemote()
            )
            val localTickets = remoteTickets.map { it.toTicketEntity() }

            whenever(remoteRepository.fetchTickets()).thenReturn(remoteTickets)

            // When
            fetchTickets.invoke()

            // Then
            verify(remoteRepository).fetchTickets()
            verify(localRepository).insertTickets(localTickets)
        }
}