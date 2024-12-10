package com.example.kyrs.Model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database (entities = [Worker::class, Employer::class, Payment::class], version = 1)
abstract class MainDB: RoomDatabase(){

    abstract fun workerDao(): WorkerDao
    abstract fun employerDao(): EmployerDao
    abstract fun paymentDao(): PaymentDao

    companion object {
         fun getDb(context: Context): MainDB {
             return Room.databaseBuilder(
                 context.applicationContext,
                 MainDB::class.java,
                 "test.db"
             ).build()
         }
     }
}
