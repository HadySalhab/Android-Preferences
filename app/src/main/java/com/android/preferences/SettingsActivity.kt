package com.android.preferences

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toolbar
import androidx.fragment.app.Fragment

class SettingsActivity : AppCompatActivity() {
private lateinit var toolbar:androidx.appcompat.widget.Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        val isFragmentContainerEmpty = savedInstanceState==null
        if(isFragmentContainerEmpty){
            supportFragmentManager.beginTransaction().add(R.id.fragment_container,SettingFragment()).commit()
        }


        toolbar = findViewById(R.id.setting_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }



}
