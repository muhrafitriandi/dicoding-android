package com.yandey.dicodingstory.presentation.ui.view.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.widget.addTextChangedListener
import com.yandey.dicodingstory.databinding.ActivityLoginBinding
import com.yandey.dicodingstory.presentation.ui.view.register.RegisterActivity
import com.yandey.dicodingstory.util.isValidEmail

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        isFormValid()
        goToRegisterActivity()
    }

    private fun setButtonEnabled() {
        binding.apply {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            val formCheck =
                (email.isNotEmpty() && email.isValidEmail()) &&
                        (password.isNotEmpty() && password.length >= 6)
            btnSignIn.isEnabled = formCheck
        }
    }

    private fun isFormValid() {
        binding.etPassword.addTextChangedListener {
            setButtonEnabled()
        }
    }

    private fun goToRegisterActivity() {
        binding.tvSignUp.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}