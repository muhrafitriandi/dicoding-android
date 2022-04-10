package com.yandey.myservice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yandey.myservice.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            btnStartService.setOnClickListener {
                val mStartServiceIntent = Intent(this@MainActivity, MyService::class.java)
                startService(mStartServiceIntent)
            }

            btnStartBoundService.setOnClickListener {
            }

            btnStopBoundService.setOnClickListener {

            }
        }
    }
}