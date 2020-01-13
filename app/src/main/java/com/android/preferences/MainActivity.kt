package com.android.preferences

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toolbar
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager

class MainActivity : AppCompatActivity(),
    PreferenceFragmentCompat.OnPreferenceStartFragmentCallback {
    private lateinit var editText: EditText
    private lateinit var settingToolbar: androidx.appcompat.widget.Toolbar
    private lateinit var btn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn = findViewById(R.id.btn_setting)
        btn.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
        editText = findViewById(R.id.editText)
        settingToolbar = findViewById(R.id.mainactivity_toolbar)
        setSupportActionBar(settingToolbar)
        supportActionBar?.title = "Settings"

    }

    override fun onStart() {
        super.onStart()
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val notificationStatus = sharedPreferences.getBoolean("notifications", false)
        val text: String
        if (notificationStatus) {
            text = "Notification is Enabled"
        } else {
            text = "Notification is Disabled"
        }

        editText.setText(text)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.item_Settings -> {
                startActivity(Intent(this, SettingsActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }


    }

    override fun onPreferenceStartFragment(
        caller: PreferenceFragmentCompat?,
        pref: Preference?
    ): Boolean {
        // Instantiate the new Fragment
        val args = pref?.extras
        val fragment =
            supportFragmentManager.fragmentFactory.instantiate(classLoader, pref!!.fragment)
        fragment.arguments = args
        fragment.setTargetFragment(caller, 0)
        // Replace the existing Fragment with the new Fragment
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
        return true
    }

}
