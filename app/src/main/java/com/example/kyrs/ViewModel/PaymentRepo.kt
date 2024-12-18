package com.example.kyrs.ViewModel

import com.example.kyrs.Model.Employer
import com.example.kyrs.Model.EmployerDao
import com.example.kyrs.Model.Payment
import com.example.kyrs.Model.PaymentDao
import com.example.kyrs.Model.Worker
import com.example.kyrs.Model.WorkerDao

class PaymentRepo(private val workerDao: WorkerDao,
                  private val employerDao: EmployerDao,
                  private val paymentDao: PaymentDao) {
    // Получить всех работников
    suspend fun getAllWorkers(){
        employerDao.getAllEmloyers()
    }

    // Получить все выплаты для работника
    suspend fun getPaymentsByWorker(workerId:Int) = paymentDao.getAllPaymentByWorker(workerId)

    //Добавить работника
    suspend fun insertWorker(worker: Worker) = workerDao.insert(worker)

    //Добавить выплату
    suspend fun insertPaymant(payment: Payment) = paymentDao.insert(payment)


    //ДОбавить работадателя
    suspend fun insertEmployer(employer: Employer) =employerDao.insert(employer)

    suspend fun getEmployerByLastNameAndPassword(lastName: String, password: String) = employerDao.getEmployerByLastNameAndPassword(lastName, password)
}

