package com.denisyordanp.truckticketapp.domain.implementation

import com.denisyordanp.truckticketapp.data.api.LocalDataRepository
import com.denisyordanp.truckticketapp.data.api.RemoteRepository
import com.denisyordanp.truckticketapp.schema.ui.Ticket
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import kotlin.random.Random

class UpdateTicketImplTest {

    @Mock
    private lateinit var localRepository: LocalDataRepository

    @Mock
    private lateinit var remoteRepository: RemoteRepository

    private lateinit var updateTicket: UpdateTicketImpl

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        updateTicket = UpdateTicketImpl(localRepository, remoteRepository, testDispatcher)
    }

    @Test
    fun `invoke updates ticket in local repository and posts ticket to remote repository`() =
        runTest(testDispatcher) {
            // Given
            val ticket = Ticket(id = Random.nextLong(), "", "", 0, 0, 0, 0)

            // When
            updateTicket.invoke(ticket)

            // Then
            verify(localRepository).updateTicket(ticket.toTicketEntity())
            verify(remoteRepository).postTicket(ticket.toTicketRemote(ticket.id))
        }
}