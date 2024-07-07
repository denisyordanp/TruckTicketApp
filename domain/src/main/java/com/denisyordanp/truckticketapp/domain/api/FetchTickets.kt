package com.denisyordanp.truckticketapp.domain.api

fun interface FetchTickets {
    suspend operator fun invoke()
}