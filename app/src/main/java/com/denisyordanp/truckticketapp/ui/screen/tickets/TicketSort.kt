package com.denisyordanp.truckticketapp.ui.screen.tickets

import com.denisyordanp.truckticketapp.schema.entity.TicketEntity

enum class TicketSort {
    DATE, DRIVER, LICENSE
}

fun TicketSort.toEntityColumn() = when(this) {
    TicketSort.DATE -> TicketEntity.DATE_TIME_COLUMN
    TicketSort.DRIVER -> TicketEntity.DRIVE_COLUMN
    TicketSort.LICENSE -> TicketEntity.LICENCE_COLUMN
}