package com.hogent.dikkeploaten.activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.hogent.dikkeploaten.R
import com.hogent.dikkeploaten.services.API
import kotlinx.android.synthetic.main.activity_registration.*

/**
 * Activity class for registration.
 */
class RegistrationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        register.setOnClickListener {
            registerNewUser()
        }
    }

    /**
     * Signs up user with given username, email and password.
     */
    private fun registerNewUser() {
        val username = username.text.toString()
        val email = email.text.toString()
        val password = password.text.toString()

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(applicationContext, "Zorg ervoor dat alles ingevuld is.", Toast.LENGTH_LONG).show()
            return
        }

        API.shared.createUser(username, email, password) { isSuccessful ->
            if (isSuccessful) {
                Toast.makeText(applicationContext, "Registratie is gelukt!", Toast.LENGTH_LONG).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(
                    applicationContext,
                    "Registratie is mislukt! Wachtwoord bevat te weinig tekens of emailadres is reeds in gebruik.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

}