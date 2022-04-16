package com.yandey.dicodingstory.presentation.ui.view

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.ViewModelProvider
import com.yandey.dicodingstory.databinding.ActivitySplashBinding
import com.yandey.dicodingstory.presentation.ui.view.login.LoginActivity
import com.yandey.dicodingstory.presentation.viewmodel.UserViewModel
import com.yandey.dicodingstory.presentation.viewmodel.UserViewModelFactory
import com.yandey.dicodingstory.util.Constant
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    @Inject
    lateinit var factory: UserViewModelFactory
    private lateinit var binding: ActivitySplashBinding
    private lateinit var viewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, factory)[UserViewModel::class.java]
        load()
    }

    private fun isLogin() {
        viewModel.getLoginToken().observe(this) { token ->
            if (token.equals("")) {
                startActivity(Intent(this, LoginActivity::class.java))
            }else {
                startActivity(Intent(this, MainActivity::class.java))
            }
            finish()
        }
    }

    private fun load() {
        Handler(Looper.getMainLooper()).postDelayed(this::isLogin, Constant.DELAY_MILLIS.toLong())
    }
}