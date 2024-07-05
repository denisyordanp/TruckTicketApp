package com.denisyordanp.truckticketapp.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.denisyordanp.truckticketapp.schema.entity.TicketEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TicketsDao {
    @Query("SELECT * FROM ${TicketEntity.TABLE_NAME}")
    fun getTickets(): Flow<List<TicketEntity>>

    @Query("SELECT * FROM '${TicketEntity.TABLE_NAME}' WHERE ${TicketEntity.LICENCE_COLUMN} = :licence LIMIT 1")
    fun getTicketDetail(licence: String): Flow<TicketEntity?>

    @Upsert
    suspend fun upsertTicket(ticket: TicketEntity)
}