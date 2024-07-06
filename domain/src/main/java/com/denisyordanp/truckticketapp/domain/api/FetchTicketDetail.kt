package com.denisyordanp.truckticketapp.domain.api

interface FetchTicketDetail {
    suspend operator fun invoke(id: Long)
}