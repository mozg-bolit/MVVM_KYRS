package com.example.kyrs.View

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.kyrs.Model.MainDB
import com.example.kyrs.Model.Worker
import com.example.kyrs.R
import com.example.kyrs.ViewModel.PaymentRepo
import com.example.kyrs.ViewModel.PaymentViewModel
import com.example.kyrs.ViewModel.PaymentViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditWorkerActivity : AppCompatActivity() {
    private lateinit var paymentViewModel: PaymentViewModel
    private var workerId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_worker)

        // Получение ID работника из Intent
        workerId = intent.getIntExtra("WORKER_ID", 0)

        // Инициализация базы данных и репозитория
        val database = MainDB.getDb(applicationContext)
        val repository = PaymentRepo(database.workerDao(), database.employerDao(), database.paymentDao())

        // Инициализация ViewModel с использованием фабрики
        val factory = PaymentViewModelFactory(repository)
        paymentViewModel = ViewModelProvider(this, factory).get(PaymentViewModel::class.java)

        // Загрузка данных работника
        loadWorkerData()

        // Установка обработчика для кнопки "Сохранить"
        val saveButton = findViewById<Button>(R.id.saveButton)
        saveButton.setOnClickListener {
            saveWorkerData()
        }
    }

    private fun loadWorkerData() {
        CoroutineScope(Dispatchers.IO).launch {
            val worker = paymentViewModel.getWorkerById(workerId)
            withContext(Dispatchers.Main) {
                if (worker != null) {
                    findViewById<EditText>(R.id.editLastName).setText(worker.lastname)
                    findViewById<EditText>(R.id.editFirstName).setText(worker.firstname)
                    findViewById<EditText>(R.id.editMiddleName).setText(worker.middlename)
                    findViewById<EditText>(R.id.editRate).setText(worker.rate.toString())
                    findViewById<EditText>(R.id.editPaymentType).setText(worker.payment_type)
                }
            }
        }
    }

    private fun saveWorkerData() {
        val lastName = findViewById<EditText>(R.id.editLastName).text.toString()
        val firstName = findViewById<EditText>(R.id.editFirstName).text.toString()
        val middleName = findViewById<EditText>(R.id.editMiddleName).text.toString()
        val rate = findViewById<EditText>(R.id.editRate).text.toString().toFloat()
        val paymentType = findViewById<EditText>(R.id.editPaymentType).text.toString()

        val updatedWorker = Worker(
            id = workerId,
            lastname = lastName,
            firstname = firstName,
            middlename = middleName,
            password = "", // Пароль можно оставить пустым или заполнить
            rate = rate,
            payment_type = paymentType
        )

        CoroutineScope(Dispatchers.IO).launch {
            paymentViewModel.updateWorker(updatedWorker)
            withContext(Dispatchers.Main) {
                Toast.makeText(this@EditWorkerActivity, "Данные сохранены", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}