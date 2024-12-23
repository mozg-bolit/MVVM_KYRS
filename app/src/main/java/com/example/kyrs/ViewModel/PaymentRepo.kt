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
    suspend fun getAllWorkers(): List<Worker> {
        return workerDao.getAllWorkers()
    }

    // Получить все выплаты для работника
    suspend fun getPaymentsByWorker(workerId: Int) = paymentDao.getAllPaymentByWorker(workerId)

    //Добавить работника
    suspend fun insertWorker(worker: Worker) = workerDao.insert(worker)

    suspend fun getWorkerByLastNameAndPassword(lastname: String, password: String): Worker? {
        return workerDao.getWorkerByLastNameAndPassword(lastname, password)
    }

    //Добавить выплату
    suspend fun insertPaymant(payment: Payment) = paymentDao.insert(payment)

    //ДОбавить работадателя
    suspend fun insertEmployer(employer: Employer) = employerDao.insert(employer)

    suspend fun getEmployerByLastNameAndPassword(lastName: String, password: String) =
        employerDao.getEmployerByLastNameAndPassword(lastName, password)

    suspend fun deleteWorker(worker: Worker) {
        workerDao.deleteWorker(worker)
    }

    suspend fun updateWorker(updatedWorker: Worker) {
        workerDao.updateWorker(
            id = updatedWorker.id,
            lastname = updatedWorker.lastname,
            firstname = updatedWorker.firstname,
            middlename = updatedWorker.middlename,
            password = updatedWorker.password,
            rate = updatedWorker.rate,
            paymentType = updatedWorker.payment_type
        )
    }

    // Получить работника по ID
    suspend fun getWorkerById(workerId: Int): Worker {
        return workerDao.getWorkerById(workerId)

    }
}
