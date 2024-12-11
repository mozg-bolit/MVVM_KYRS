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

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val RDbutton = findViewById<Button>(R.id.RDB)
        val Rbutton = findViewById<Button>(R.id.RB)

        RDbutton.setOnClickListener {
            val intent = Intent(this, RDForm::class.java)
            startActivity(intent)
        }

        Rbutton.setOnClickListener {
            val intent = Intent(this, RForm::class.java)
            startActivity(intent)
        }
    }
}