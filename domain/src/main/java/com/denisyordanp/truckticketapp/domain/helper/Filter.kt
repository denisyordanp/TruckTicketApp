package com.denisyordanp.truckticketapp.domain.helper

import com.denisyordanp.truckticketapp.common.extension.toFormattedDateString
import com.denisyordanp.truckticketapp.common.util.DateFormat
import com.denisyordanp.truckticketapp.common.util.TicketParam
import com.denisyordanp.truckticketapp.schema.ui.Ticket

fun List<Ticket>.filterByParam(filter: Pair<TicketParam, String>?) =
    this.let { original ->
        filter?.let { f ->
            original.filter {
                val value = when (f.first) {
                    TicketParam.DATE -> it.dateTime.toFormattedDateString(DateFormat.DAY_MONTH_YEAR)
                    TicketParam.DRIVER -> it.driver
                    TicketParam.LICENSE -> it.licence
                }

                value.equals(f.second, true)
            }
        } ?: original
    }