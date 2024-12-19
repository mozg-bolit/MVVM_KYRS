package com.example.kyrs.View

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.kyrs.Model.MainDB
import com.example.kyrs.Model.Worker
import com.example.kyrs.Model.WorkerInput
import com.example.kyrs.R
import com.example.kyrs.ViewModel.PaymentRepo
import com.example.kyrs.ViewModel.PaymentViewModel
import com.example.kyrs.ViewModel.PaymentViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CreateWorkers : AppCompatActivity() {
    private lateinit var paymentViewModel: PaymentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_create_workers)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val database = MainDB.getDb(applicationContext)
        val repository = PaymentRepo(database.workerDao(), database.employerDao(), database.paymentDao())

        val factory = PaymentViewModelFactory(repository)
        paymentViewModel = ViewModelProvider(this, factory).get(PaymentViewModel::class.java)

        val addWorkerButton: Button = findViewById(R.id.AddWorkerBT)

        addWorkerButton.setOnClickListener {
            createWorker()
        }
    }

    private fun createWorker() {
        val workerInput = getInput()

        if (workerInput.lastname.isNotEmpty() && workerInput.firstname.isNotEmpty() &&
            workerInput.middlename.isNotEmpty() && workerInput.password.isNotEmpty() && workerInput.rate > 0) {

            val newWorker = Worker(
                lastname = workerInput.lastname,
                firstname = workerInput.firstname,
                middlename = workerInput.middlename,
                password = workerInput.password,
                rate = workerInput.rate,
                payment_type = workerInput.paymentType
            )

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    paymentViewModel.insertWorker(newWorker)
                    withContext(Dispatchers.Main) {
                        clearInput()
                        Toast.makeText(applicationContext,"Работник успешно добавлен", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(applicationContext,"Ошибка при добавлении работника: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        } else {
            Toast.makeText(applicationContext,"Заполните все поля корректно", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getInput(): WorkerInput {
        val lastname = findViewById<EditText>(R.id.editWLN).text.toString().trim()
        val firstname = findViewById<EditText>(R.id.editWN).text.toString().trim()
        val middlename = findViewById<EditText>(R.id.editWMN).text.toString().trim()
        val password = findViewById<EditText>(R.id.editWP).text.toString().trim()

        // Получаем тариф из EditText и конвертируем в Float
        val rateString = findViewById<EditText>(R.id.editRateW).text.toString().trim()

        // Проверяем корректность ввода тарифа
        val rate = if (rateString.isNotEmpty()) {
            try {
                rateString.toFloat()
            } catch (e: NumberFormatException) {
                Toast.makeText(applicationContext,"Некорректный тариф", Toast.LENGTH_SHORT).show()
                return WorkerInput("", "", "", "", 0.0f, "") // Возвращаем пустые значения при ошибке
            }
        } else {
            0.0f // Значение по умолчанию
        }

        // Получаем выбранное значение из Spinner
        val spinner = findViewById<Spinner>(R.id.spinner)
        val selectedItem = spinner.selectedItem.toString()

        return WorkerInput(lastname, firstname, middlename, password, rate, selectedItem)
    }

    private fun clearInput() {
        findViewById<EditText>(R.id.editWLN).text.clear()
        findViewById<EditText>(R.id.editWN).text.clear()
        findViewById<EditText>(R.id.editWMN).text.clear()
        findViewById<EditText>(R.id.editWP).text.clear()
    }
}
