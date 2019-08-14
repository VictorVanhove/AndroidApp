package com.example.dikkeploaten.Activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.dikkeploaten.R.layout.activity_login)

        login.setOnClickListener {
            loginUser()
        }

        register.setOnClickListener {
            startActivity(Intent(applicationContext, RegistrationActivity::class.java))
        }

    }

    private fun loginUser() {
        val email = email.text.toString()
        val password = password.text.toString()

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
                        val intent = Intent(applicationContext, MainActivity::class.java)
                        startActivity(intent)
                    }
                    else
                    {
                        Toast.makeText(applicationContext, "Login failed! Please try again later", Toast.LENGTH_LONG).show()
                    }
                }
            })
    }

}