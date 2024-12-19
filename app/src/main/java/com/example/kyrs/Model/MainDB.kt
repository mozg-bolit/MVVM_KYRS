package com.example.kyrs.Model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Worker::class, Employer::class, Payment::class], version = 1)
abstract class MainDB : RoomDatabase() {
    abstract fun workerDao(): WorkerDao
    abstract fun employerDao(): EmployerDao
    abstract fun paymentDao(): PaymentDao

    companion object {
        @Volatile
        private var INSTANCE: MainDB? = null

        fun getDb(context: Context): MainDB {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MainDB::class.java,
                    "main_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
