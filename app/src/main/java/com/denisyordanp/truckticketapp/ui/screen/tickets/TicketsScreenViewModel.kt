package com.denisyordanp.truckticketapp.ui.screen.tickets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.denisyordanp.truckticketapp.common.extension.pairOf
import com.denisyordanp.truckticketapp.common.util.TicketParam
import com.denisyordanp.truckticketapp.domain.api.FetchTickets
import com.denisyordanp.truckticketapp.domain.api.GetTicketFilter
import com.denisyordanp.truckticketapp.domain.api.GetTruckTickets
import com.denisyordanp.truckticketapp.util.UiState
import com.denisyordanp.truckticketapp.util.safeCallWrapper
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
    getTicketFilter: GetTicketFilter,
    private val fetchTickets: FetchTickets
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

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

    val filterDate = getTicketFilter.invoke<Long>(TicketParam.DATE).stateIn(
        viewModelScope,
        Lazily,
        emptyList()
    )

    val filterLicence = getTicketFilter.invoke<String>(TicketParam.LICENSE).stateIn(
        viewModelScope,
        Lazily,
        emptyList()
    )

    val filterDriver = getTicketFilter.invoke<String>(TicketParam.DRIVER).stateIn(
        viewModelScope,
        Lazily,
        emptyList()
    )

    fun loadTickets() = viewModelScope.launch {
        safeCallWrapper(
            call = { fetchTickets() },
            onStart = { _uiState.emit(UiState.loading()) },
            onFinish = { _uiState.emit(UiState.success()) },
            onError = { _uiState.emit(UiState.error(it)) }
        )
    }

    fun selectSort(sort: TicketParam) = viewModelScope.launch {
        _ticketSort.emit(sort)
    }

    fun selectFilter(filter: Pair<TicketParam, String>?) = viewModelScope.launch {
        _ticketFilter.emit(filter)
    }
}