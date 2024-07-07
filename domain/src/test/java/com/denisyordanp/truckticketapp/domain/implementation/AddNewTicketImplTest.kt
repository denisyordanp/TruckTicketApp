package com.denisyordanp.truckticketapp.domain.implementation

import com.denisyordanp.truckticketapp.data.api.LocalDataRepository
import com.denisyordanp.truckticketapp.data.api.RemoteRepository
import com.denisyordanp.truckticketapp.schema.ui.Ticket
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.random.Random

class AddNewTicketImplTest {
    @Mock
    private lateinit var localRepository: LocalDataRepository

    @Mock
    private lateinit var remoteRepository: RemoteRepository

    private lateinit var addNewTicket: AddNewTicketImpl

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        addNewTicket = AddNewTicketImpl(localRepository, remoteRepository, testDispatcher)
    }

    @Test
    fun `invoke adds new ticket to local repository and posts to remote repository`() =
        runTest(testDispatcher) {
            // Given
            val ticketId = Random.nextLong()
            val ticket = Ticket(id = ticketId, "", "", 0, 0, 0, 0)
            val ticketEntity = ticket.toTicketEntity()
            val remoteTicket = ticket.toTicketRemote(ticketId)

            whenever(localRepository.insertTicket(ticketEntity)).thenReturn(ticketId)

            // When
            addNewTicket.invoke(ticket)

            // Then
            verify(localRepository).insertTicket(ticketEntity)
            verify(remoteRepository).postTicket(remoteTicket)
        }
}