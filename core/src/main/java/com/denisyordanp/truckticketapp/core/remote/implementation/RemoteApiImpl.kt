package com.denisyordanp.truckticketapp.core.remote.implementation

import com.denisyordanp.truckticketapp.core.remote.api.RemoteApi
import com.denisyordanp.truckticketapp.schema.remote.TicketRemote
import com.denisyordanp.truckticketapp.schema.remote.TicketRemote.Companion.DATE_TIME
import com.denisyordanp.truckticketapp.schema.remote.TicketRemote.Companion.DRIVER
import com.denisyordanp.truckticketapp.schema.remote.TicketRemote.Companion.ID
import com.denisyordanp.truckticketapp.schema.remote.TicketRemote.Companion.INBOUND
import com.denisyordanp.truckticketapp.schema.remote.TicketRemote.Companion.LICENCE
import com.denisyordanp.truckticketapp.schema.remote.TicketRemote.Companion.OUTBOUND
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class RemoteApiImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
) : RemoteApi {

    companion object {
        private const val COLLECTION_PATH = "tickets"
    }

    override suspend fun writeTicket(ticket: TicketRemote) = suspendCoroutine { coroutine ->
        firestore.collection(COLLECTION_PATH).document(ticket.id.toString())
            .set(ticket.toHasMap())
            .addOnSuccessListener {
                coroutine.resume(Unit)
            }.addOnFailureListener {
                coroutine.resumeWithException(it)
            }
    }

    override suspend fun readTickets() = suspendCoroutine { coroutine ->
        firestore.collection(COLLECTION_PATH)
            .get(Source.SERVER)
            .addOnSuccessListener { result ->
                val response = result.map { it.toRemoteTicket() }

                coroutine.resume(response)
            }.addOnFailureListener {
                coroutine.resumeWithException(it)
            }
    }

    override suspend fun readTicket(id: Long) = suspendCoroutine { coroutine ->
        firestore.collection(COLLECTION_PATH).document(id.toString())
            .get(Source.SERVER)
            .addOnSuccessListener { result ->
                val response =
                    if (result.exists()) result.toRemoteTicket() else null
                coroutine.resume(response)
            }.addOnFailureListener {
                coroutine.resumeWithException(it)
            }
    }

    private fun DocumentSnapshot.toRemoteTicket() = TicketRemote(
        id = this[ID],
        licence = this[LICENCE],
        driver = this[DRIVER],
        inbound = this[INBOUND],
        outbound = this[OUTBOUND],
        dateTime = this[DATE_TIME]
    )
}