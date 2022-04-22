package com.yandey.dicodingstory.ui.settings

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import com.yandey.dicodingstory.R
import com.yandey.dicodingstory.databinding.ActivitySettingsBinding
import com.yandey.dicodingstory.ui.login.LoginActivity
import com.yandey.dicodingstory.util.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private val viewModel: SettingsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        signOutButton()
        setTheme()
        backToActivity()
        changeLanguage()
    }

    private fun signOutButton() {
        binding.btnSignOut.setOnClickListener {
            viewModel.logout()
            startActivity(Intent(this@SettingsActivity, LoginActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            })
            this@SettingsActivity.toast(getString(R.string.sign_out_success))
        }
    }

    private fun setTheme() {
        binding.swTheme.setOnCheckedChangeListener { _, isChecked ->
            viewModel.saveThemeSetting(isChecked)
        }

        lifecycleScope.launch {
            viewModel.getThemeSetting().collect { isDarkModeActive ->
                if (isDarkModeActive == true) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    binding.swTheme.isChecked = true
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    binding.swTheme.isChecked = false
                }
            }
        }
    }

    private fun changeLanguage() {
        binding.tvChangeLanguage.setOnClickListener {
            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
        }
    }

    private fun backToActivity() {
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
    }
}