package com.yandey.dicodingstory.presentation.ui.view.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.yandey.dicodingstory.R
import com.yandey.dicodingstory.data.model.body.LoginBody
import com.yandey.dicodingstory.databinding.ActivityLoginBinding
import com.yandey.dicodingstory.presentation.ui.view.MainActivity
import com.yandey.dicodingstory.presentation.ui.view.register.RegisterActivity
import com.yandey.dicodingstory.presentation.viewmodel.UserViewModel
import com.yandey.dicodingstory.presentation.viewmodel.UserViewModelFactory
import com.yandey.dicodingstory.util.Constant
import com.yandey.dicodingstory.util.Resource
import com.yandey.dicodingstory.util.isValidEmail
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    @Inject
    lateinit var factory: UserViewModelFactory
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, factory)[UserViewModel::class.java]

        isFormValid()
        signInButton()
        observeResponse()
        goToRegisterActivity()
    }

    private fun signInButton() {
        binding.apply {
            btnSignIn.setOnClickListener {
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()
                viewModel.login(LoginBody(email, password))
            }
        }
    }

    private fun observeResponse() {
        viewModel.loginUser.observe(this) {
            when (it) {
                is Resource.Success -> {
                    showLoading(false)
                    Toast.makeText(this, it.data?.message, Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java).apply {
                        viewModel.saveLoginToken(it.data?.loginResult?.token.toString())
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    })
                    finishAndRemoveTask()
                }

                is Resource.Error -> {
                    showLoading(false)
                    Toast.makeText(this, getString(R.string.user_not_found), Toast.LENGTH_SHORT).show()
                }
                is Resource.Loading -> {
                    showLoading(true)
                }
            }
        }
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
        binding.apply {
            etEmail.addTextChangedListener {
                setButtonEnabled()
            }
            etPassword.addTextChangedListener {
                setButtonEnabled()
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.INVISIBLE
        }
    }

    private fun goToRegisterActivity() {
        binding.tvSignUp.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}