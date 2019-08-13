package com.example.dikkeploaten.Fragments

import android.os.Bundle
import androidx.preference.EditTextPreference
import androidx.preference.PreferenceFragmentCompat
import com.example.dikkeploaten.R
import com.example.dikkeploaten.Services.API

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        val usernamePreference: EditTextPreference? = findPreference("username")
        val passwordPreference: EditTextPreference? = findPreference("password")

        if(usernamePreference?.text != null && passwordPreference?.text != null) {
            API.shared.updateUsername(usernamePreference.text)
            API.shared.updatePassword(passwordPreference.text)
        }
    }

}