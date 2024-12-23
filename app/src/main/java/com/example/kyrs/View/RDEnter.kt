package com.example.kyrs.View

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.kyrs.Model.MainDB // Импортируйте класс базы данных
import com.example.kyrs.R
import com.example.kyrs.ViewModel.PaymentRepo
import com.example.kyrs.ViewModel.PaymentViewModel
import com.example.kyrs.ViewModel.PaymentViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RDEnter : AppCompatActivity() {
    private lateinit var paymentViewModel: PaymentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_rdenter)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val RDEnter_BT = findViewById<Button>(R.id.REnter_BT)

        // Инициализация базы данных и репозитория
        val database = MainDB.getDb(applicationContext)
        val repository = PaymentRepo(database.workerDao(), database.employerDao(), database.paymentDao())

        // Инициализация ViewModel с использованием фабрики
        val factory = PaymentViewModelFactory(repository)
        paymentViewModel = ViewModelProvider(this, factory).get(PaymentViewModel::class.java)

        RDEnter_BT.setOnClickListener {
            enter()
        }
    }

    private fun enter() {
        val lastName = findViewById<EditText>(R.id.editLNER).text.toString().trim()
        val password = findViewById<EditText>(R.id.editPER).text.toString().trim()

        if (lastName.isNotEmpty() && password.isNotEmpty()) {
            // Проверка данных в базе данных
            checkCredentials(lastName, password)
        } else {
            Toast.makeText(this, "Введите все данные", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkCredentials(lastName: String, password: String) {
        // Запуск проверки в корутине для работы с базой данных
        CoroutineScope(Dispatchers.IO).launch {val isValidUser = paymentViewModel.validateUser(lastName, password)

            withContext(Dispatchers.Main) {
                if (isValidUser) {

                    // Переход на другое активити при успешной проверке
                    val intent = Intent(this@RDEnter, RDProfile::class.java)
                    startActivity(intent)

                } else {
                    Toast.makeText(this@RDEnter, "Неверные данные", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
