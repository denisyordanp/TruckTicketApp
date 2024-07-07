package com.denisyordanp.truckticketapp.domain.api

import com.denisyordanp.truckticketapp.common.util.TicketParam
import kotlinx.coroutines.flow.Flow

interface GetTicketFilter {
    fun <T> invoke(filter: TicketParam): Flow<List<T>>
}