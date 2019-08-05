package com.example.dikkeploaten.Activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.dikkeploaten.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class RegistrationActivity: AppCompatActivity() {

    private lateinit var emailTV: EditText
    private lateinit var passwordTV: EditText
    private lateinit var regBtn: Button
    private lateinit var progressBar: ProgressBar

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        auth = FirebaseAuth.getInstance()

        initializeUI()

        regBtn.setOnClickListener {
            registerNewUser()
        }
    }

    private fun registerNewUser() {
        progressBar.visibility = View.VISIBLE

        val email = emailTV.text.toString()
        val password = passwordTV.text.toString()

        if (TextUtils.isEmpty(email))
        {
            Toast.makeText(applicationContext, "Please enter email...", Toast.LENGTH_LONG).show()
            return
        }
        if (TextUtils.isEmpty(password))
        {
            Toast.makeText(applicationContext, "Please enter password!", Toast.LENGTH_LONG).show()
            return
        }

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(object: OnCompleteListener<AuthResult> {
                override fun onComplete(task:Task<AuthResult>) {
                    if (task.isSuccessful)
                    {
                        Toast.makeText(applicationContext, "Registration successful!", Toast.LENGTH_LONG).show()
                        progressBar.visibility = View.GONE
                        val intent = Intent(applicationContext, MainActivity::class.java)
                        startActivity(intent)
                    }
                    else
                    {
                        Toast.makeText(applicationContext, "Registration failed! Please try again later", Toast.LENGTH_LONG).show()
                        progressBar.visibility = View.GONE
                    }
                }
            })
    }

    private fun initializeUI() {
        emailTV = findViewById(R.id.email)
        passwordTV = findViewById(R.id.password)
        regBtn = findViewById(R.id.register)
        progressBar = findViewById(R.id.progressBar)
    }
}