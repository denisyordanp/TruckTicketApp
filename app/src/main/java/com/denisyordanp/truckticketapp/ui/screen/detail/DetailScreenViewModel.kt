package com.denisyordanp.truckticketapp.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.denisyordanp.truckticketapp.common.extension.safeCallWrapper
import com.denisyordanp.truckticketapp.domain.api.FetchTicketDetail
import com.denisyordanp.truckticketapp.domain.api.GetTicketDetail
import com.denisyordanp.truckticketapp.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailScreenViewModel @Inject constructor(
    private val getTicketDetail: GetTicketDetail,
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
        SharingStarted.Lazily,
        null
    )

    fun loadTicket(id: Long) = viewModelScope.launch {
        _id.emit(id)

        safeCallWrapper(
            call = { fetchTicketDetail(id) },
            onStart = { _uiState.emit(UiState.loading()) },
            onFinish = { _uiState.emit(UiState.success()) },
            onError = { _uiState.emit(UiState.error(it)) }
        )
    }
}