package com.example.kyrs.ViewModel

import androidx.lifecycle.ViewModel
import com.example.kyrs.Model.Employer
import com.example.kyrs.Model.Payment
import com.example.kyrs.Model.Worker

class PaymentViewModel(private val repo: PaymentRepo): ViewModel() {


   //Получит всех работников
   suspend fun getAllWorkers(): List<Worker> {
       return repo.getAllWorkers()
   }


    //Получутить выплаты для работника
 suspend fun getPaymentByIDWorker(workerId:Int) = repo.getPaymentsByWorker(workerId)

    //Добавить работника
 suspend fun insertWorker(worker: Worker) = repo.insertWorker(worker)

    //Добавить выплату
 suspend fun insertPayment(payment: Payment) = repo.insertPaymant(payment)

 suspend fun getWorkerByLastNameAndPassword(lastname: String, password: String) = repo.getWorkerByLastNameAndPassword(lastname, password)


    //Добавить работадателя
 suspend fun insertEmployer(employer: Employer) = repo.insertEmployer(employer)


    suspend fun validateUser(lastName: String, password: String): Boolean {
        // Здесь должна быть логика для проверки пользователя в базе данных.
        val employer = repo.getEmployerByLastNameAndPassword(lastName,password)
        return employer != null // Возвращает true если пользователь найден
    }

    suspend fun validateWorker(lastname: String, password: String): Boolean{
        val worker = repo.getWorkerByLastNameAndPassword(lastname, password)
        return  worker != null
    }

    suspend fun deleteWorker(worker: Worker) {
        repo.deleteWorker(worker)}

    suspend fun updateWorker(updatedWorker: Worker) {
        repo.updateWorker(updatedWorker)
    }

    // Получить работника по ID
    suspend fun getWorkerById(workerId: Int): Worker? {
        return repo.getWorkerById(workerId)
    }


}