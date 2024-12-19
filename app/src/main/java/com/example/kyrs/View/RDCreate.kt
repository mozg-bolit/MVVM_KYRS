package com.example.kyrs.View

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.kyrs.Model.Employer
import com.example.kyrs.R
import com.example.kyrs.Model.MainDB // Импортируйте класс базы данных
import com.example.kyrs.ViewModel.PaymentRepo
import com.example.kyrs.ViewModel.PaymentViewModel
import com.example.kyrs.ViewModel.PaymentViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RDCreate : AppCompatActivity() {
    private lateinit var paymentViewModel: PaymentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_rdcreate)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Инициализация базы данных и репозитория
        val database = MainDB.getDb(applicationContext) // Получаем экземпляр базы данных
        val repository = PaymentRepo(database.workerDao(), database.employerDao(), database.paymentDao())

        // Инициализация ViewModel с использованием фабрики, если требуется передать параметры
        val factory = PaymentViewModelFactory(repository)
        paymentViewModel = ViewModelProvider(this, factory).get(PaymentViewModel::class.java)

        val createRB: Button = findViewById(R.id.CreateRB)
        val exit: Button = findViewById(R.id.Back)

        createRB.setOnClickListener {
            createUser()
        }

        exit.setOnClickListener {
            finish()
        }
    }

    private fun createUser() {
        val (lastName, firstName, middleName, password) = getInputFields()

        if (lastName.isNotEmpty() && firstName.isNotEmpty() &&
            middleName.isNotEmpty() && password.isNotEmpty()) {
            val newEmployer = Employer(lastName = lastName, firstName = firstName, middleName = middleName, password = password)

            // Запускаем корутину для вставки в базу данных
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    paymentViewModel.insertEmployer(newEmployer)
                    withContext(Dispatchers.Main) {
                        clearInputFields()
                        Toast.makeText(applicationContext,"Работодатель успешно добавлен", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(applicationContext, "Ошибка при добавлении работодателя: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        } else {
            Toast.makeText(applicationContext, "Заполните все поля", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getInputFields(): List<String> {
        val lastName = findViewById<EditText>(R.id.editCRDSurname).text.toString().trim()
        val firstName = findViewById<EditText>(R.id.editCRDName).text.toString().trim()
        val middleName = findViewById<EditText>(R.id.editCRDPatronymic).text.toString().trim()
        val password = findViewById<EditText>(R.id.editCRDPass).text.toString().trim()

        return listOf(lastName, firstName, middleName, password)
    }

    private fun clearInputFields() {
        findViewById<EditText>(R.id.editCRDSurname).text.clear()
        findViewById<EditText>(R.id.editCRDName).text.clear()
        findViewById<EditText>(R.id.editCRDPatronymic).text.clear()
        findViewById<EditText>(R.id.editCRDPass).text.clear()
    }
}
