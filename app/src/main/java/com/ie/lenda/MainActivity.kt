package com.ie.lenda

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar

class MainActivity : AppCompatActivity() {

    private fun setupAppBar(){
        val appBar = findViewById<Toolbar>(R.id.app_bar)
        setSupportActionBar(appBar)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupAppBar()
    }
}
