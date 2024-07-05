package com.denisyordanp.truckticketapp.schema.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.denisyordanp.truckticketapp.common.extension.orZero
import com.denisyordanp.truckticketapp.schema.ui.Ticket

@Entity(tableName = TicketEntity.TABLE_NAME)
data class TicketEntity(
    @PrimaryKey
    @ColumnInfo(LICENCE_COLUMN)
    val licence: String,

    @ColumnInfo(DRIVE_COLUMN)
    val driver: String,

    @ColumnInfo(INBOUND_COLUMN)
    val inbound: Long,

    @ColumnInfo(OUTBOUND_COLUMN)
    val outbound: Long?,

    @ColumnInfo(DATE_TIME_COLUMN)
    val dateTime: Long
) {
    companion object {
        const val TABLE_NAME = "ticket"
        const val LICENCE_COLUMN = "licence"
        const val DRIVE_COLUMN = "driver"
        const val INBOUND_COLUMN = "inbound"
        const val OUTBOUND_COLUMN = "outbound"
        const val DATE_TIME_COLUMN = "date_time"
    }

    fun mapToTicket() = Ticket(
        licence = licence,
        driver = driver,
        inbound = inbound,
        outbound = outbound,
        dateTime = dateTime,
        netWeight = outbound.orZero().minus(inbound)
    )
}
