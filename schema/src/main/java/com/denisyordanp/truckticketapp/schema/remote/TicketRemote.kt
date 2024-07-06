package com.denisyordanp.truckticketapp.schema.remote

import com.denisyordanp.truckticketapp.common.extension.anyToLong
import com.denisyordanp.truckticketapp.common.extension.toString
import com.denisyordanp.truckticketapp.schema.entity.TicketEntity
import com.google.firebase.firestore.DocumentSnapshot

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
        licence = licence.toString(),
        driver = driver.toString(),
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
        private const val ID = "id"
        private const val LICENCE = "licence"
        private const val DRIVER = "driver"
        private const val INBOUND = "inbound"
        private const val OUTBOUND = "outbound"
        private const val DATE_TIME = "dateTime"

        fun fromDocument(query: DocumentSnapshot) = TicketRemote(
            id = query[ID],
            licence = query[LICENCE],
            driver = query[DRIVER],
            inbound = query[INBOUND],
            outbound = query[OUTBOUND],
            dateTime = query[DATE_TIME]
        )
    }
}
