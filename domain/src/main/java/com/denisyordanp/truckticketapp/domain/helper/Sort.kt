package com.denisyordanp.truckticketapp.domain.helper

import com.denisyordanp.truckticketapp.common.util.TicketParam
import com.denisyordanp.truckticketapp.schema.ui.Ticket

fun Ticket.getSorted(sort: TicketParam): String = when (sort) {
    TicketParam.DATE -> this.dateTime.toString()
    TicketParam.DRIVER -> this.driver
    TicketParam.LICENSE -> this.licence
}