package com.example.kyrs.Model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface EmployerDao {
    @Insert
    fun insert(employer: Employer)

    @Query("SELECT * FROM emoloyers")
    fun getAllEmloyers(): List<Employer>

    @Query("SELECT * FROM emoloyers WHERE id =:id")
    fun getPaymentByEmployer(id:Int): List<Employer>
}