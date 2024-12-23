package com.example.kyrs.View

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
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

class WorkersActivity : AppCompatActivity() {
    private lateinit var paymentViewModel: PaymentViewModel
    private lateinit var workersContainer: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workers)

        workersContainer = findViewById(R.id.workersContainer)

        // Инициализация базы данных и репозитория
        val database = MainDB.getDb(applicationContext)
        val repository = PaymentRepo(database.workerDao(), database.employerDao(), database.paymentDao())

        // Инициализация ViewModel с использованием фабрики
        val factory = PaymentViewModelFactory(repository)
        paymentViewModel = ViewModelProvider(this, factory).get(PaymentViewModel::class.java)

        // Загрузка данных из базы данных
        loadWorkers()
    }

    private fun loadWorkers() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Получаем список работников из ViewModel
                val workers = paymentViewModel.getAllWorkers()

                withContext(Dispatchers.Main) {
                    if (workers.isNotEmpty()) {
                        // Очищаем контейнер перед добавлением новых работников
                        workersContainer.removeAllViews()

                        // Добавляем каждого работника в контейнер
                        for (worker in workers) {
                            addWorkerToLayout(worker)
                        }
                    } else {
                        Toast.makeText(this@WorkersActivity, "Нет работников", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@WorkersActivity, "Ошибка загрузки данных: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun addWorkerToLayout(worker: Worker) {
        // Создание нового элемента программно
        val workerView = LayoutInflater.from(this).inflate(R.layout.item_worker, workersContainer, false) as LinearLayout

        // Установка текста с информацией о работнике
        val workerInfoTextView = workerView.findViewById<TextView>(R.id.workerInfo)
        workerInfoTextView.text = "${worker.lastname} ${worker.firstname} ${worker.middlename} - ${worker.payment_type} ${worker.rate}"

        // Установка обработчика для кнопки "Редактировать"
        val editButton = workerView.findViewById<Button>(R.id.editButton)
        editButton.setOnClickListener {
            val intent = Intent(this, EditWorkerActivity::class.java)
            intent.putExtra("WORKER_ID", worker.id)
            startActivity(intent)
        }

        // Установка обработчика для кнопки "Удалить"
        val deleteButton = workerView.findViewById<Button>(R.id.deleteButton)
        deleteButton.setOnClickListener {
            deleteWorker(worker)
        }

        // Добавление в контейнер
        workersContainer.addView(workerView)
    }

    private fun deleteWorker(worker: Worker) {
        CoroutineScope(Dispatchers.IO).launch {
            paymentViewModel.deleteWorker(worker)
            withContext(Dispatchers.Main) {
                loadWorkers()
            }
        }
    }
}