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
import com.example.kyrs.Model.MainDB
import com.example.kyrs.R
import com.example.kyrs.ViewModel.PaymentRepo
import com.example.kyrs.ViewModel.PaymentViewModel
import com.example.kyrs.ViewModel.PaymentViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RForm : AppCompatActivity() {
    private lateinit var paymentViewModel: PaymentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_rform)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val RDEnter_BT = findViewById<Button>(R.id.EnterWB)

        // Инициализация базы данных и репозитория
        val database = MainDB.getDb(applicationContext)
        val repository = PaymentRepo(database.workerDao(), database.employerDao(), database.paymentDao())

        // Инициализация ViewModel с использованием фабрики
        val factory = PaymentViewModelFactory(repository)
        paymentViewModel = ViewModelProvider(this, factory).get(PaymentViewModel::class.java)

        RDEnter_BT.setOnClickListener {
            // Получение данных из полей ввода
            val lastname = findViewById<EditText>(R.id.editLoginR).text.toString().trim()
            val password = findViewById<EditText>(R.id.editPassR).text.toString().trim()

            // Проверка, что все поля заполнены
            if (lastname.isNotEmpty() && password.isNotEmpty()) {
                // Передача данных через Intent
                val intent = Intent(this, Work::class.java)
                intent.putExtra("LASTNAME", lastname)
                intent.putExtra("PASSWORD", password)

                // Запуск активности Work
                startActivity(intent)

                // Проверка данных в базе данных
                checkCredentials(lastname, password)
            } else {
                Toast.makeText(this, "Введите фамилию и пароль", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkCredentials(lastname: String, password: String) {
        // Запуск проверки в корутине для работы с базой данных
        CoroutineScope(Dispatchers.IO).launch {
            val isValidUser = paymentViewModel.validateWorker(lastname, password)

            withContext(Dispatchers.Main) {
                if (isValidUser) {
                    Toast.makeText(this@RForm, "Данные верны", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@RForm, "Неверные данные", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}