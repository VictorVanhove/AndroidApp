package com.example.dikkeploaten.Fragments

import android.app.Activity
import android.content.Intent
import android.graphics.ImageDecoder
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.preference.EditTextPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.example.dikkeploaten.Activities.LoginActivity
import com.example.dikkeploaten.Services.API
import com.google.firebase.auth.FirebaseAuth
import java.io.IOException


class SettingsFragment : PreferenceFragmentCompat() {

    private var SELECT_PROFILEIMAGE = 0
    private var SELECT_COVERIMAGE = 1

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode === SELECT_PROFILEIMAGE) {
            if (resultCode === Activity.RESULT_OK) {
                if (data != null) {
                    try {
                        val source = ImageDecoder.createSource(activity!!.contentResolver, data.data!!)
                        val bitmap = ImageDecoder.decodeBitmap(source)
                        API.shared.uploadProfileImage(bitmap)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                }
            } else if (resultCode === Activity.RESULT_CANCELED) {
                Toast.makeText(activity, "Canceled", Toast.LENGTH_SHORT).show()
            }
        }
        if (requestCode === SELECT_COVERIMAGE) {
            if (resultCode === Activity.RESULT_OK) {
                if (data != null) {
                    try {
                        val source = ImageDecoder.createSource(activity!!.contentResolver, data.data!!)
                        val bitmap = ImageDecoder.decodeBitmap(source)
                        API.shared.uploadCoverImage(bitmap)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                }
            } else if (resultCode === Activity.RESULT_CANCELED) {
                Toast.makeText(activity, "Canceled", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onPreferenceTreeClick(preference: Preference?): Boolean {
        when (preference!!.key) {
            "profile" -> {
                val intent = Intent()
                intent.type = "image/*"
                intent.action = Intent.ACTION_GET_CONTENT
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PROFILEIMAGE)
            }
            "cover" -> {
                val intent = Intent()
                intent.type = "image/*"
                intent.action = Intent.ACTION_GET_CONTENT
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_COVERIMAGE)
            }
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