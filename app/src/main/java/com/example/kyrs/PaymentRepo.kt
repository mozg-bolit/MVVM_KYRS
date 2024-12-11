package com.example.kyrs

import com.example.kyrs.Model.EmployerDao
import com.example.kyrs.Model.Payment
import com.example.kyrs.Model.PaymentDao
import com.example.kyrs.Model.Worker
import com.example.kyrs.Model.WorkerDao

class PaymentRepo(private val workerDao: WorkerDao,
                  private val employerDao: EmployerDao,
                  private val paymentDao: PaymentDao) {
    // Получить всех работников
    fun getAllWorkers(){
        employerDao.getAllEmloyers()
    }

    // Получить все выплаты для работника
    fun getPaymentsByWorker(workerId:Int) = paymentDao.getAllPaymentByWorker(workerId)

    //Добавить работника
    fun insertWorker(worker: Worker) = workerDao.insert(worker)

    //Добавить выплату
    fun insertPaymant(payment: Payment) = paymentDao.insert(payment)


}