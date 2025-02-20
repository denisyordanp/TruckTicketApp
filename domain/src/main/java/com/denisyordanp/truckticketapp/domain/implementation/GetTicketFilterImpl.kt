package com.denisyordanp.truckticketapp.domain.implementation

import com.denisyordanp.truckticketapp.common.di.IoDispatcher
import com.denisyordanp.truckticketapp.common.extension.toFormattedDateString
import com.denisyordanp.truckticketapp.common.util.DateFormat
import com.denisyordanp.truckticketapp.common.util.TicketParam
import com.denisyordanp.truckticketapp.data.api.LocalDataRepository
import com.denisyordanp.truckticketapp.domain.api.GetTicketFilter
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject

class GetTicketFilterImpl @Inject constructor(
    private val repository: LocalDataRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : GetTicketFilter {
    @OptIn(ExperimentalCoroutinesApi::class)
    @Suppress("UNCHECKED_CAST")
    override fun <T> filter(filter: TicketParam) = repository.getTickets()
        .mapLatest { tickets ->
            when (filter) {
                TicketParam.DATE -> tickets.distinctBy {
                    it.dateTime.toFormattedDateString(
                        DateFormat.DAY_MONTH_YEAR
                    )
                }.map { it.dateTime as T }

                TicketParam.DRIVER -> tickets.distinctBy { it.driver }.map { it.driver as T }
                TicketParam.LICENSE -> tickets.distinctBy { it.licence }.map { it.licence as T }
            }.take(5)
        }.flowOn(dispatcher)
}