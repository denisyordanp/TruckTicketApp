package com.denisyordanp.truckticketapp.ui.screen.tickets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.denisyordanp.truckticketapp.domain.api.GetTruckTickets
import com.denisyordanp.truckticketapp.schema.ui.Ticket
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.Lazily
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class TicketsScreenViewModel @Inject constructor(
    private val getTruckTickets: GetTruckTickets
) : ViewModel() {

    private val _ticketSort = MutableStateFlow(TicketSort.DATE)
    val ticketSort = _ticketSort.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val tickets = _ticketSort
        .flatMapLatest { sort ->
            getTruckTickets().mapLatest { tickets ->
                tickets.sortedBy { it.getSorted(sort) }
            }
        }.stateIn(
            viewModelScope,
            Lazily,
            emptyList()
        )

    private fun Ticket.getSorted(sort: TicketSort): String = when (sort) {
        TicketSort.DATE -> this.dateTime.toString()
        TicketSort.DRIVER -> this.driver
        TicketSort.LICENSE -> this.licence
    }

    fun selectSort(sort: TicketSort) {
        _ticketSort.value = sort
    }
}