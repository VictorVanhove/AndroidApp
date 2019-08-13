package com.example.dikkeploaten.Fragments

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.preference.EditTextPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.example.dikkeploaten.Activities.LoginActivity
import com.example.dikkeploaten.Services.API
import com.google.firebase.auth.FirebaseAuth


class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(com.example.dikkeploaten.R.xml.preferences, rootKey)


        val usernamePreference: EditTextPreference? = findPreference("username")
        val passwordPreference: EditTextPreference? = findPreference("password")

        usernamePreference?.summaryProvider = Preference.SummaryProvider<EditTextPreference> { preference ->
            val text = preference.text
            if (TextUtils.isEmpty(text)) {
                "Not set"
            } else {
                API.shared.updateUsername(text)
                text
            }
        }
        passwordPreference?.summaryProvider = Preference.SummaryProvider<EditTextPreference> { preference ->
            val text = preference.text
            if (TextUtils.isEmpty(text)) {
                "Not set"
            } else {
                API.shared.updatePassword(text)
                encryptPassword(text)
            }
        }
    }

    override fun onPreferenceTreeClick(preference: Preference?): Boolean {
        when (preference!!.key) {
            "logout" -> {
                FirebaseAuth.getInstance().signOut()

                val i = Intent(context, LoginActivity::class.java)
                i.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(i)
            }


        }

        return super.onPreferenceTreeClick(preference)
    }

    private fun encryptPassword(password: String): String {
        var encryptedPassword = ""
        for(x in 0 until password.length) {
            encryptedPassword += "*"
        }
        return encryptedPassword
    }

}