package com.yandey.ticketingapps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yandey.ticketingapps.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            finishButton.setOnClickListener {
                seatsView.seat?.let {
                    AppUtils.showToast(
                        this@MainActivity,
                        resources.getString(R.string.seat_number, it.name)
                    )
                } ?: run {
                    AppUtils.showToast(
                        this@MainActivity,
                        resources.getString(R.string.please_choose_seat_first)
                    )
                }
            }
        }
    }
}