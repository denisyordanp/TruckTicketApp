package com.denisyordanp.truckticketapp.ui.screen.manage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.denisyordanp.truckticketapp.common.extension.safeCallWrapper
import com.denisyordanp.truckticketapp.domain.api.AddNewTicket
import com.denisyordanp.truckticketapp.domain.api.FetchTicketDetail
import com.denisyordanp.truckticketapp.domain.api.GetTicketDetail
import com.denisyordanp.truckticketapp.domain.api.UpdateTicket
import com.denisyordanp.truckticketapp.schema.ui.Ticket
import com.denisyordanp.truckticketapp.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.Lazily
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ManageScreenViewModel @Inject constructor(
    private val addNewTicket: AddNewTicket,
    private val getTicketDetail: GetTicketDetail,
    private val updateTicket: UpdateTicket,
    private val fetchTicketDetail: FetchTicketDetail
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    private val _id = MutableStateFlow<Long?>(null)

    @OptIn(ExperimentalCoroutinesApi::class)
    val ticket = _id.flatMapLatest { id ->
        id?.let { getTicketDetail(it) } ?: emptyFlow()
    }.stateIn(
        viewModelScope,
        Lazily,
        null
    )

    fun add(ticket: Ticket) = viewModelScope.launch {
        addNewTicket(ticket)
    }

    fun loadTicket(id: Long) = viewModelScope.launch {
        _id.emit(id)

        safeCallWrapper(
            call = { fetchTicketDetail(id) },
            onStart = { _uiState.emit(UiState.loading()) },
            onFinish = { _uiState.emit(UiState.success()) },
            onError = { _uiState.emit(UiState.error(it)) }
        )
    }

    fun update(ticket: Ticket) = viewModelScope.launch {
        updateTicket(ticket)
    }
}