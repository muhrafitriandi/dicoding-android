package com.yandey.dicodingstory.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import com.yandey.dicodingstory.ui.main.MainActivity
import com.yandey.dicodingstory.ui.login.LoginActivity
import com.yandey.dicodingstory.ui.settings.SettingsViewModel
import com.yandey.dicodingstory.util.Constant.DELAY_MILLIS
import com.yandey.dicodingstory.util.Constant.USER_TOKEN
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private val splashViewModel: SplashViewModel by viewModels()
    private val settingsViewModel: SettingsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadingScreen()
    }

    private fun isLogin() {
        lifecycleScope.launchWhenCreated {
            launch {
                settingsViewModel.getThemeSetting().collect { isDarkModeActive ->
                    if (isDarkModeActive == true) {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    } else {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    }
                    splashViewModel.getToken().collect { token ->
                        if (token.isNullOrEmpty()) {
                            startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                            finish()
                        } else {
                            Intent(this@SplashActivity, MainActivity::class.java).also { intent ->
                                intent.putExtra(USER_TOKEN, token)
                                startActivity(intent)
                                finish()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun loadingScreen() {
        Handler(Looper.getMainLooper()).postDelayed(this::isLogin, DELAY_MILLIS.toLong())
    }
}