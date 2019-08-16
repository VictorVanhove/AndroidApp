package com.hogent.dikkeploaten.activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.hogent.dikkeploaten.R.layout.activity_login)

        login.setOnClickListener {
            loginUser()
        }

        register.setOnClickListener {
            startActivity(Intent(this, RegistrationActivity::class.java))
        }

    }

    /**
     * Logs in user to app.
     */
    private fun loginUser() {
        val email = email.text.toString()
        val password = password.text.toString()

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password))
        {
            Toast.makeText(applicationContext, "Zorg ervoor dat alles ingevuld is.", Toast.LENGTH_LONG).show()
            return
        }

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(applicationContext, "Login is succesvol!", Toast.LENGTH_LONG).show()
                    val intent = Intent(applicationContext, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(applicationContext, "Login is mislukt! Probeer opnieuw.", Toast.LENGTH_LONG).show()
                }
            }
    }

}