package com.example.kyrs.Model

import androidx.room.Dao
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
}