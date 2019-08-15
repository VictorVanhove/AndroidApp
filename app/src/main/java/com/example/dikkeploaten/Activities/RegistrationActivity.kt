package com.example.dikkeploaten.Activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.dikkeploaten.R
import com.example.dikkeploaten.Services.API
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_registration.*

class RegistrationActivity: AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        auth = FirebaseAuth.getInstance()

        register.setOnClickListener {
            registerNewUser()
        }
    }

    private fun registerNewUser() {
        val username = username.text.toString()
        val email = email.text.toString()
        val password = password.text.toString()

        if (TextUtils.isEmpty(email))
        {
            Toast.makeText(this, "Please enter email...", Toast.LENGTH_LONG).show()
            return
        }
        if (TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Please enter password!", Toast.LENGTH_LONG).show()
            return
        }

        API.shared.createUser(username, email, password) {
            Toast.makeText(this, "Registration successful!", Toast.LENGTH_LONG).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

}