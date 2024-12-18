package com.example.kyrs.Model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface EmployerDao {
    @Insert
    suspend fun insert(employer: Employer)

    @Query("SELECT * FROM employers")
    suspend fun getAllEmloyers(): List<Employer>

    @Query("SELECT * FROM employers WHERE id =:id")
    suspend fun getPaymentByEmployer(id:Int): List<Employer>

    @Query("SELECT * FROM employers WHERE lastname = :lastName AND password = :password LIMIT 1")
    suspend fun getEmployerByLastNameAndPassword(lastName: String, password: String): Employer?
}
