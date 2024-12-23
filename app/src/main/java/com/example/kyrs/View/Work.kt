package com.example.kyrs.View

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
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

class Work : AppCompatActivity() {
    private lateinit var paymentViewModel: PaymentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_work)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Инициализация базы данных и репозитория
        val database = MainDB.getDb(applicationContext)
        val repository = PaymentRepo(database.workerDao(), database.employerDao(), database.paymentDao())

        // Инициализация ViewModel с использованием фабрики
        val factory = PaymentViewModelFactory(repository)
        paymentViewModel = ViewModelProvider(this, factory).get(PaymentViewModel::class.java)

        // Получение данных из Intent
        val lastname = intent.getStringExtra("LASTNAME")
        val password = intent.getStringExtra("PASSWORD")

        if (lastname != null && password != null) {
            // Загрузка данных работника по фамилии и паролю
            loadWorkerData(lastname, password)
        } else {
            Toast.makeText(this, "Фамилия и пароль не переданы", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadWorkerData(lastname: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Получаем работника по фамилии и паролю
                val worker = paymentViewModel.getWorkerByLastNameAndPassword(lastname, password)

                withContext(Dispatchers.Main) { // Исправлено на Dispatchers.Main
                    if (worker != null) {
                        displayWorkerData(worker)
                    } else {
                        Toast.makeText(this@Work, "Работник не найден", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@Work, "Ошибка загрузки данных: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun displayWorkerData(worker: Worker) {
        try {
            if (worker.lastname != null && worker.firstname != null && worker.middlename != null && worker.payment_type != null && worker.rate != null) {
                val lastnameInfo = "Фамилия: ${worker.lastname}"
                val firstnameInfo = "Имя: ${worker.firstname}"
                val middlenameInfo = "Отчество: ${worker.middlename}"
                val paymentInfo = "Тариф: ${worker.payment_type} - ${worker.rate}"

                val lnV: TextView = findViewById(R.id.lnV)
                val fnV: TextView = findViewById(R.id.fnV)
                val mnV: TextView = findViewById(R.id.mnV)
                val prV: TextView = findViewById(R.id.prV)

                lnV.text = lastnameInfo
                fnV.text = firstnameInfo
                mnV.text = middlenameInfo
                prV.text = paymentInfo

                Log.d("WorkActivity", "Данные отображены: $lastnameInfo, $firstnameInfo, $middlenameInfo, $paymentInfo")
            } else {
                Toast.makeText(this, "Данные о работнике отсутствуют", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Log.e("WorkActivity", "Ошибка при отображении данных работника: ${e.message}", e)
            Toast.makeText(this, "Ошибка: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
}