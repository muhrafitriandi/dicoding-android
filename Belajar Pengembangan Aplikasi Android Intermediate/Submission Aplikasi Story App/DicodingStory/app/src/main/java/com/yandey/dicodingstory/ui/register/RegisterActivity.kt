package com.yandey.dicodingstory.ui.register

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.core.widget.addTextChangedListener
import com.yandey.dicodingstory.R
import com.yandey.dicodingstory.data.model.RegisterBody
import com.yandey.dicodingstory.databinding.ActivityRegisterBinding
import com.yandey.dicodingstory.ui.login.LoginActivity
import com.yandey.dicodingstory.util.isValidEmail
import com.yandey.dicodingstory.util.setTransparentStatusBar
import com.yandey.dicodingstory.util.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: RegisterViewModel by viewModels()
    private var job: Job = Job()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        moveToLoginActivity()
        isFormValid()
        registerButton()
        playAnimation()
        this.setTransparentStatusBar()
    }

    private fun registerButton() {
        binding.apply {
            btnSignUp.setOnClickListener {
                val name = etName.text.toString()
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()
                showLoading(true)
                lifecycleScope.launchWhenResumed {
                    if (job.isActive) job.cancel()
                    job = launch {
                        viewModel.register(RegisterBody(name, email, password)).collect { result ->
                            result.onSuccess { response ->
                                finishAndRemoveTask()
                                this@RegisterActivity.toast(response.message)
                                showLoading(false)
                            }
                            result.onFailure {
                                this@RegisterActivity.toast(getString(R.string.email_is_already_taken))
                                showLoading(false)
                            }
                        }
                    }
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

    private fun moveToLoginActivity() {
        binding.tvSignIn.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            if (isLoading) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.INVISIBLE
            }
        }
    }

    @SuppressLint("Recycle")
    private fun playAnimation() {
        binding.apply {
            val view1 = ObjectAnimator.ofFloat(tvCreateAccount, View.ALPHA, 1f).setDuration(250)
            val view2 =
                ObjectAnimator.ofFloat(tvSignUpToGetStarted, View.ALPHA, 1f).setDuration(250)
            val name = ObjectAnimator.ofFloat(etName, View.ALPHA, 1f).setDuration(250)
            val email = ObjectAnimator.ofFloat(etEmail, View.ALPHA, 1f).setDuration(250)
            val password = ObjectAnimator.ofFloat(etPassword, View.ALPHA, 1f).setDuration(250)
            val signUp = ObjectAnimator.ofFloat(btnSignUp, View.ALPHA, 1f).setDuration(250)
            val view4 =
                ObjectAnimator.ofFloat(tvAlreadyHaveAnAccount, View.ALPHA, 1f).setDuration(250)
            val view5 = ObjectAnimator.ofFloat(tvSignIn, View.ALPHA, 1f).setDuration(250)

            val groupAnimation = AnimatorSet().apply {
                playTogether(view4, view5)
            }

            AnimatorSet().apply {
                playSequentially(view1, view2, name, email, password, signUp, groupAnimation)
                start()
            }
        }
    }
}