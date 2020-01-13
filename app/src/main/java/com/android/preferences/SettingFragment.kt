package com.android.preferences

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.*

class SettingFragment : PreferenceFragmentCompat() {
    private lateinit var settingToolbar: androidx.appcompat.widget.Toolbar
    private var editTextPreference: EditTextPreference? = null
    private var googlePreference: Preference? = null
    private var switchNotificationPreference: SwitchPreferenceCompat? = null


    private var screenModeListPreference: ListPreference? = null

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        editTextPreference = findPreference<EditTextPreference>("pref_cache_duration")
        switchNotificationPreference = findPreference("notifications")
        screenModeListPreference = findPreference("pref_screen_mode")

        val notificationSwitchPreferenceChangeListener =
            object : Preference.OnPreferenceChangeListener {
                override fun onPreferenceChange(preference: Preference?, newValue: Any?): Boolean {
                    if (newValue == false) {
                        Toast.makeText(
                            activity,
                            "Notification option is disabled",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        Toast.makeText(
                            activity,
                            "Notification option is enabled",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    return true

                }

            }
        switchNotificationPreference!!.setOnPreferenceChangeListener(
            notificationSwitchPreferenceChangeListener
        )

        editTextPreference!!.summaryProvider =
            Preference.SummaryProvider<EditTextPreference> { preference ->
                val text = preference.text
                if (text.isEmpty()) {
                    "Not Set"
                } else {
                    "Cache duration: $text"
                }

            }

        googlePreference = findPreference("key_google")
        val googleIntent = Intent(Intent.ACTION_VIEW)
        googleIntent.data = Uri.parse("http://www.google.com")
        googlePreference!!.intent = googleIntent


        screenModeListPreference?.setOnPreferenceChangeListener(object :
            Preference.OnPreferenceChangeListener {
            override fun onPreferenceChange(preference: Preference?, newValue: Any?): Boolean {
                when (newValue) {
                    getString(R.string.dark) -> updateScreenMode(AppCompatDelegate.MODE_NIGHT_YES)
                    getString(R.string.light) -> updateScreenMode(AppCompatDelegate.MODE_NIGHT_NO)
                    getString(R.string.system_default) -> updateScreenMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                }
                return true
            }

        })


    }

    private fun updateScreenMode(mode: Int) {
        AppCompatDelegate.setDefaultNightMode(mode)
        requireActivity().recreate()
    }


}