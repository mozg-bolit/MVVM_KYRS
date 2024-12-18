package com.example.kyrs.DataBase

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.example.kyrs.Model.Employer
import com.example.kyrs.Model.EmployerDao
import com.example.kyrs.Model.Payment
import com.example.kyrs.Model.PaymentDao
import com.example.kyrs.Model.Worker
import com.example.kyrs.Model.WorkerDao

@Database(entities = [Worker::class, Employer::class, Payment::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun workerDao(): WorkerDao
    abstract fun employerDao(): EmployerDao
    abstract fun paymentDao(): PaymentDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
