package com.example.kyrs.Model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PaymentDao {
    @Insert
    suspend fun insert(payment: Payment)

    @Query("SELECT * FROM payments WHERE workerId =:workerId")
    suspend fun getAllPaymentByWorker(workerId:Int): List<Payment>

    @Query("SELECT * FROM payments WHERE employerId =:employerId")
    suspend fun getPaymentByEmployer(employerId: Int): List<Payment>

    @Query("SELECT * FROM payments WHERE id =:paymentId")
    suspend fun getPaymentById(paymentId: Int): List<Payment>
}