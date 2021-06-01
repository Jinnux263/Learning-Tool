package com.example.learningtool

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.learningtool.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        val View = binding.root
        setContentView(View)

        val fragment = SetClockFragment()
        val fm = getSupportFragmentManager()
        val transaction = fm.beginTransaction()
        transaction.replace(R.id.placeholder, fragment)
        transaction.commit()
    }
}