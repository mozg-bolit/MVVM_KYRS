package com.example.kyrs.View

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.kyrs.R

class RDForm : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_rdform)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val CreatRD: Button = findViewById(R.id.CreateRD)
        val EnterRD: Button = findViewById(R.id.EnterRD)

        CreatRD.setOnClickListener {
            val intent = Intent(this, RDCreate::class.java)
            startActivity(intent)
        }

        EnterRD.setOnClickListener{
           val intent = Intent(this, RDEnter::class.java)
            startActivity(intent)
        }
    }
}