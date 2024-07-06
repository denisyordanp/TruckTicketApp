package com.denisyordanp.truckticketapp.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.denisyordanp.truckticketapp.domain.api.GetTicketDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailScreenViewModel @Inject constructor(
    private val getTicketDetail: GetTicketDetail
) : ViewModel() {

    private val _id = MutableStateFlow<Long?>(null)

    @OptIn(ExperimentalCoroutinesApi::class)
    val ticket = _id.flatMapLatest { id ->
        id?.let { getTicketDetail(it) } ?: emptyFlow()
    }.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        null
    )

    fun setId(id: Long) = viewModelScope.launch {
        _id.emit(id)
    }

}