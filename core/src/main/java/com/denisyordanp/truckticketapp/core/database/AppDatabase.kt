package com.denisyordanp.truckticketapp.core.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.denisyordanp.truckticketapp.core.database.dao.TicketsDao
import com.denisyordanp.truckticketapp.schema.entity.TicketEntity

@Database(
    entities = [TicketEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun ticketDao(): TicketsDao

    companion object {
        private const val DATABASE_NAME = "trucker_ticket_database"
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: createDatabase(context)
        }

        private fun createDatabase(context: Context): AppDatabase {
            val db = Room.databaseBuilder(
                context,
                AppDatabase::class.java, DATABASE_NAME
            ).build()

            INSTANCE = db
            return db
        }
    }
}