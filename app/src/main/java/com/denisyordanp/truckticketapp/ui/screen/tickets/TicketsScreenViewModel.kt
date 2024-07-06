package com.denisyordanp.truckticketapp.ui.screen.tickets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.denisyordanp.truckticketapp.common.extension.pairOf
import com.denisyordanp.truckticketapp.common.util.TicketParam
import com.denisyordanp.truckticketapp.domain.api.GetTicketFilter
import com.denisyordanp.truckticketapp.domain.api.GetTruckTickets
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.Lazily
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TicketsScreenViewModel @Inject constructor(
    private val getTruckTickets: GetTruckTickets,
    private val getTicketFilter: GetTicketFilter
) : ViewModel() {

    private val _ticketSort = MutableStateFlow(TicketParam.DATE)
    val ticketSort = _ticketSort.asStateFlow()

    private val _ticketFilter = MutableStateFlow<Pair<TicketParam, String>?>(null)
    val ticketFilter = _ticketFilter.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val tickets = combine(_ticketSort, ticketFilter) { sort, filter ->
        pairOf(sort, filter)
    }.flatMapLatest { getTruckTickets(it.first, it.second) }.stateIn(
        viewModelScope,
        Lazily,
        emptyList()
    )

    val filterDate = getTicketFilter<Long>(TicketParam.DATE).stateIn(
        viewModelScope,
        Lazily,
        emptyList()
    )

    val filterLicence = getTicketFilter<String>(TicketParam.LICENSE).stateIn(
        viewModelScope,
        Lazily,
        emptyList()
    )

    val filterDriver = getTicketFilter<String>(TicketParam.DRIVER).stateIn(
        viewModelScope,
        Lazily,
        emptyList()
    )

    fun selectSort(sort: TicketParam) = viewModelScope.launch {
        _ticketSort.emit(sort)
    }

    fun selectFilter(filter: Pair<TicketParam, String>?) = viewModelScope.launch {
        _ticketFilter.emit(filter)
    }
}