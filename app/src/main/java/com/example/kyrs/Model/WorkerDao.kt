package com.example.kyrs.Model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface WorkerDao {
    @Insert
    suspend fun insert(worker: Worker)


    @Query("SELECT *FROM workers")
    suspend fun getAllWorkers(): List<Worker>


    @Query("SELECT * FROM workers WHERE lastname =:lastname AND password =:password LIMIT 1")
    suspend fun getWorkerByLastNameAndPassword(lastname:String, password:String): Worker?


    @Query("SELECT * FROM workers WHERE id =:workerId")
    suspend fun getWorkerById(workerId:Int): Worker

    @Query("SELECT * FROM workers ORDER BY id DESC LIMIT 1")
    suspend fun getLastWorker(): Worker?

    @Delete
    suspend fun deleteWorker(worker: Worker)

    @Query("UPDATE workers SET lastname = :lastname, name = :firstname, middlename = :middlename, password = :password, rate = :rate, payment_type = :paymentType WHERE id = :id")
    suspend fun updateWorker(
        id: Int,
        lastname: String,
        firstname: String,
        middlename: String,
        password: String,
        rate: Float,
        paymentType: String
    )
}