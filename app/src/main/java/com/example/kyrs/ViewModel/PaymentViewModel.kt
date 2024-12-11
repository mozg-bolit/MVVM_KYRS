package com.example.kyrs.ViewModel


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.kyrs.Model.MainDB
import com.example.kyrs.Model.Payment
import com.example.kyrs.Model.Worker

class PaymentViewModel(): ViewModel() {

    private val repo = PaymentRepo()/////!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

   //Получит всех работников
    fun getAllWorkers() = repo.getAllWorkers()

    //Получутить выплаты для работника
    fun getPaymentByIDWorker(workerId:Int) = repo.getPaymentsByWorker(workerId)

    //Добавить работника
    fun insertWorker(worker: Worker) = repo.insertWorker(worker)

    //Добавить выплату
    fun insertPayment(payment: Payment) = repo.insertPaymant(payment)
}