package com.example.dikkeploaten.Fragments

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.preference.EditTextPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.example.dikkeploaten.Services.API


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
                text
            }
        }
    }

        }
    }

}