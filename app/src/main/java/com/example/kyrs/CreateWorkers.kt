package com.example.kyrs

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.kyrs.Model.MainDB
import com.example.kyrs.Model.Worker
import com.example.kyrs.ViewModel.PaymentRepo
import com.example.kyrs.ViewModel.PaymentViewModel
import com.example.kyrs.ViewModel.PaymentViewModelFactory

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

        val AddWorker:Button = findViewById(R.id.AddWorkerBT)

        val datebase = MainDB.getDb(applicationContext)
        val reposiotry = PaymentRepo(datebase.workerDao(), datebase.employerDao(), datebase.paymentDao())

        val factory = PaymentViewModelFactory(reposiotry)
        paymentViewModel = ViewModelProvider(this, factory).get(PaymentViewModel::class.java)


        AddWorker.setOnClickListener{
            val (lastname, firstname, middlename, password) = getinput()




            

//Добавить rate(ТАРИФ оплаты для убрания ошибки)







            if (lastname.isNotEmpty() && firstname.isNotEmpty() && middlename.isNotEmpty() && password.isNotEmpty()){
                val newWorker = Worker(lastname = lastname,firstname = firstname,middlename = middlename, password = password)
            }

        }



    }
    fun getinput(): List<String>{
        val lastname = findViewById<EditText>(R.id.editLNER).text.toString().trim()
        val firstname = findViewById<EditText>(R.id.editWN).text.toString().trim()
        val middlename = findViewById<EditText>(R.id.editWMN).text.toString().trim()
        val password = findViewById<EditText>(R.id.editWP).text.toString().trim()

        return listOf(lastname, firstname, middlename, password)
    }
}