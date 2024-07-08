package com.denisyordanp.truckticketapp.schema.remote

import com.denisyordanp.truckticketapp.common.extension.anyToLong
import com.denisyordanp.truckticketapp.common.extension.anyToString
import com.denisyordanp.truckticketapp.schema.entity.TicketEntity

data class TicketRemote(
    val id: Any?,
    val licence: Any?,
    val driver: Any?,
    val inbound: Any?,
    val outbound: Any?,
    val dateTime: Any?
) {
    fun toTicketEntity() = TicketEntity(
        id = id.anyToLong(),
        licence = licence.anyToString(),
        driver = driver.anyToString(),
        inbound = inbound.anyToLong(),
        outbound = outbound.anyToLong(),
        dateTime = dateTime.anyToLong()
    )

    fun toHasMap() = hashMapOf(
        ID to id,
        LICENCE to licence,
        DRIVER to driver,
        INBOUND to inbound,
        OUTBOUND to outbound,
        DATE_TIME to dateTime
    )

    companion object {
        const val ID = "id"
        const val LICENCE = "licence"
        const val DRIVER = "driver"
        const val INBOUND = "inbound"
        const val OUTBOUND = "outbound"
        const val DATE_TIME = "dateTime"
    }
}
