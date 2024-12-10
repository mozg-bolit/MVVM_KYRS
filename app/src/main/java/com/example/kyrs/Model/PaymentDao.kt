package com.example.kyrs.Model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PaymentDao {
    @Insert
    fun insert(payment: Payment)

    @Query("SELECT * FROM payments WHERE workerId =:workerId")
    fun getAllPaymentByWorker(workerId:Int): List<Payment>

    @Query("SELECT * FROM payments WHERE employerId =:employerId")
    fun getPaymentByEmployer(employerId: Int): List<Payment>

    @Query("SELECT * FROM payments WHERE id =:paymentId")
    fun getPaymentById(paymentId: Int): List<Payment>
}