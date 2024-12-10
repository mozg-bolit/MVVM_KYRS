package com.example.kyrs.Model

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.kyrs.R

class RDCreate : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_rdcreate)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val CreateRB: Button = findViewById(R.id.CreateRB)
        val Exit: Button = findViewById(R.id.Back)

        CreateRB.setOnClickListener {
            val lastName = findViewById<EditText>(R.id.editCRDSurname)
            val Name = findViewById<EditText>(R.id.editCRDName)
            val middleName = findViewById<EditText>(R.id.editCRDPatronymic)
            val password = findViewById<EditText>(R.id.editCRDPass)
        }

        Exit.setOnClickListener {
            finish()
        }
    }
}
