package com.denisyordanp.truckticketapp.domain.api

interface FetchTickets {
    suspend operator fun invoke()
}