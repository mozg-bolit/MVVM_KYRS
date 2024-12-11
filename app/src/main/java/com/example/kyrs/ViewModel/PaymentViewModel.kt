package com.example.kyrs.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.kyrs.Model.MainDB
import com.example.kyrs.Model.Payment
import com.example.kyrs.Model.Worker

class PaymentViewModel(application: Application): AndroidViewModel(application) {
    private val repo: PaymentRepo

    init {
        val db = MainDB.getDb(application)
        repo = PaymentRepo(db.workerDao(), db.paymentDao())
    }

    //Получит всех работников
    fun getAllWorkers() = repo.getAllWorkers()

    //Получутить выплаты для работника
    fun getPaymentByIDWorker(workerId:Int) = repo.getPaymentsByWorker(workerId)

    //Добавить работника
    fun insertWorker(worker: Worker) = repo.insertWorker(worker)

    //Добавить выплату
    fun insertPayment(payment: Payment) = repo.insertPaymant(payment)
}