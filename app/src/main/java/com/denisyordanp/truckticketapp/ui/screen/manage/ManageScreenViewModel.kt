package com.denisyordanp.truckticketapp.ui.screen.manage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.denisyordanp.truckticketapp.domain.api.AddNewTicket
import com.denisyordanp.truckticketapp.domain.api.GetTicketDetail
import com.denisyordanp.truckticketapp.domain.api.UpdateTicket
import com.denisyordanp.truckticketapp.schema.ui.Ticket
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.Lazily
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ManageScreenViewModel @Inject constructor(
    private val addNewTicket: AddNewTicket,
    private val getTicketDetail: GetTicketDetail,
    private val updateTicket: UpdateTicket
) : ViewModel() {

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

    fun setId(id: Long) = viewModelScope.launch {
        _id.emit(id)
    }

    fun update(ticket: Ticket) = viewModelScope.launch {
        updateTicket(ticket)
    }
}