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
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var emailTV: EditText
    private lateinit var passwordTV: EditText
    private lateinit var loginBtn: Button
    private lateinit var regBtn: Button
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.dikkeploaten.R.layout.activity_login)

        initializeUI()

        loginBtn.setOnClickListener {
            loginUser()
        }

        regBtn.setOnClickListener {
            startActivity(Intent(applicationContext, RegistrationActivity::class.java))
        }

    }

    private fun loginUser() {
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

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(object: OnCompleteListener<AuthResult> {
                override fun onComplete(task: Task<AuthResult>) {
                    if (task.isSuccessful)
                    {
                        Toast.makeText(applicationContext, "Login successful!", Toast.LENGTH_LONG).show()
                        progressBar.visibility = View.GONE
                        val intent = Intent(applicationContext, MainActivity::class.java)
                        startActivity(intent)
                    }
                    else
                    {
                        Toast.makeText(applicationContext, "Login failed! Please try again later", Toast.LENGTH_LONG).show()
                        progressBar.visibility = View.GONE
                    }
                }
            })
    }

    private fun initializeUI() {
        emailTV = findViewById(com.example.dikkeploaten.R.id.email)
        passwordTV = findViewById(com.example.dikkeploaten.R.id.password)
        loginBtn = findViewById(com.example.dikkeploaten.R.id.login)
        regBtn = findViewById(com.example.dikkeploaten.R.id.register)
        progressBar = findViewById(com.example.dikkeploaten.R.id.progressBar)
    }

}