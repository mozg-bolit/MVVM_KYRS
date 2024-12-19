package com.example.kyrs.View

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
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

        // Обработка нажатия кнопки "Вход"
        val enterButton: Button = findViewById(R.id.button)
        enterButton.setOnClickListener {
            loginWorker()
        }
    }

    private fun loginWorker() {
        val lastname = findViewById<EditText>(R.id.editLoginR).text.toString().trim()
        val password = findViewById<EditText>(R.id.editPassR).text.toString().trim()

        if (lastname.isNotEmpty() && password.isNotEmpty()) {
            CoroutineScope(Dispatchers.IO).launch {
                // Проверка данных работника в базе данных
                val worker = paymentViewModel.getWorkerByLastNameAndPassword(lastname, password)

                withContext(Dispatchers.Main) {
                    if (worker != null) {
                        // Отображение данных о работнике в TextView
                        displayWorkerData(worker)
                    } else {
                        Toast.makeText(applicationContext, "Неверная фамилия или пароль", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        } else {
            Toast.makeText(applicationContext, "Введите фамилию и пароль", Toast.LENGTH_SHORT).show()
        }
    }

    private fun displayWorkerData(worker: Worker) {
        // Отображение данных о работнике в TextView.
        findViewById<TextView>(R.id.textView8).text = "Фамилия: ${worker.lastname}"
        findViewById<TextView>(R.id.textView9).text = "Имя: ${worker.firstname}"
        findViewById<TextView>(R.id.textView10).text = "Отчество: ${worker.middlename}"
        findViewById<TextView>(R.id.textView11).text = "Тариф: ${worker.payment_type} (${worker.rate})"
    }
}
