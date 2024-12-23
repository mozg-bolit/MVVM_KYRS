package com.example.kyrs.View

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.kyrs.R

class RDProfile : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_rdprofile)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val AddWorker: Button = findViewById(R.id.EDDRD)
        val WSpis = findViewById<Button>(R.id.RSpis)

        AddWorker.setOnClickListener{
            val intent = Intent(this@RDProfile, CreateWorkers::class.java)
            startActivity(intent)
        }

        WSpis.setOnClickListener {
            val intent = Intent(this@RDProfile, WorkersActivity::class.java)
            startActivity(intent)
        }

    }
}