package com.denisyordanp.truckticketapp.ui.screen.manage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.denisyordanp.truckticketapp.domain.api.AddNewTicket
import com.denisyordanp.truckticketapp.schema.ui.Ticket
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ManageScreenViewModel @Inject constructor(
    private val addNewTicket: AddNewTicket
) : ViewModel() {

    fun addTicket(ticket: Ticket) = viewModelScope.launch {
        addNewTicket(ticket)
    }

    fun loadTicket(licence: String) = viewModelScope.launch {
        TODO("implement load")
    }

    fun updateTicket(ticket: Ticket) = viewModelScope.launch {
        TODO("implement update")
    }
}