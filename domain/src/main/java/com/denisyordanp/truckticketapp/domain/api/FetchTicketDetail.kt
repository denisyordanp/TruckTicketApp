package com.denisyordanp.truckticketapp.domain.api

fun interface FetchTicketDetail {
    suspend operator fun invoke(id: Long)
}