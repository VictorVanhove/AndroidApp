package com.hogent.dikkeploaten.fragments

import android.app.Activity
import android.content.Intent
import android.graphics.ImageDecoder
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.preference.EditTextPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.google.firebase.auth.FirebaseAuth
import com.hogent.dikkeploaten.activities.SettingsActivity
import com.hogent.dikkeploaten.services.API
import java.io.IOException


class SettingsFragment : PreferenceFragmentCompat() {

    private var profileImageCode = 0
    private var profileCoverCode = 1

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(com.hogent.dikkeploaten.R.xml.preferences, rootKey)

        val usernamePreference: EditTextPreference? = findPreference("username")
        val passwordPreference: EditTextPreference? = findPreference("password")

        usernamePreference?.summaryProvider = Preference.SummaryProvider<EditTextPreference> { preference ->
            val text = preference.text
            if (TextUtils.isEmpty(text)) {
                API.shared.cache.user.username
            } else {
                API.shared.updateUsername(text)
                text
            }
        }
        passwordPreference?.summaryProvider = Preference.SummaryProvider<EditTextPreference> { preference ->
            val text = preference.text
            if (TextUtils.isEmpty(text)) {
                "********"
            } else {
                API.shared.updatePassword(text)
                encryptPassword(text)
            }
        }
    }

    /**
     * Handles image gallery smartphone for profile image/cover.
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode === profileImageCode) {
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
        if (requestCode === profileCoverCode) {
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

    /**
     * Handles click event for selected preference.
     */
    override fun onPreferenceTreeClick(preference: Preference?): Boolean {
        when (preference!!.key) {
            "profile" -> {
                val intent = Intent()
                intent.type = "image/*"
                intent.action = Intent.ACTION_GET_CONTENT
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), profileImageCode)
            }
            "cover" -> {
                val intent = Intent()
                intent.type = "image/*"
                intent.action = Intent.ACTION_GET_CONTENT
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), profileCoverCode)
            }
            "logout" -> {
                FirebaseAuth.getInstance().signOut()

                val activity = context as SettingsActivity
                activity.signOutUser()
            }


        }

        return super.onPreferenceTreeClick(preference)
    }

    /**
     * Replaces password into dots.
     */
    private fun encryptPassword(password: String): String {
        var encryptedPassword = ""
        for (x in 0 until password.length) {
            encryptedPassword += "*"
        }
        return encryptedPassword
    }

}