package com.yandey.dicodingstory.presentation.ui.view.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.yandey.dicodingstory.R
import com.yandey.dicodingstory.data.model.body.RegisterBody
import com.yandey.dicodingstory.databinding.ActivityRegisterBinding
import com.yandey.dicodingstory.presentation.ui.view.login.LoginActivity
import com.yandey.dicodingstory.presentation.viewmodel.UserViewModel
import com.yandey.dicodingstory.presentation.viewmodel.UserViewModelFactory
import com.yandey.dicodingstory.util.Resource
import com.yandey.dicodingstory.util.isValidEmail
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: UserViewModelFactory
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, factory)[UserViewModel::class.java]

        isFormValid()
        signUpButton()
        observeResponse()
        goToLoginActivity()
    }

    private fun signUpButton() {
        binding.apply {
            btnSignUp.setOnClickListener {
                val name = etName.text.toString()
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()
                viewModel.register(RegisterBody(name, email, password))
            }
        }
    }

    private fun observeResponse() {
        viewModel.registerUser.observe(this) {
            when (it) {
                is Resource.Success -> {
                    showLoading(false)
                    Toast.makeText(this, it.data?.message.toString(), LENGTH_SHORT).show()
                    finishAndRemoveTask()
                }
                is Resource.Error -> {
                    showLoading(false)
                    Toast.makeText(this, getString(R.string.email_is_already_taken), LENGTH_SHORT)
                        .show()
                }
                is Resource.Loading -> {
                    showLoading(true)
                }
            }
        }
    }

    private fun setButtonEnabled() {
        binding.apply {
            val name = etName.text.toString()
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            val formCheck =
                (name.isNotEmpty()) &&
                        (email.isNotEmpty() && email.isValidEmail()) &&
                        (password.isNotEmpty() && password.length >= 6)
            btnSignUp.isEnabled = formCheck
        }
    }

    private fun isFormValid() {
        binding.apply {
            etName.addTextChangedListener {
                setButtonEnabled()
            }
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

    private fun goToLoginActivity() {
        binding.tvSignIn.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}