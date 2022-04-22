package com.yandey.dicodingstory.ui.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.yandey.dicodingstory.R
import com.yandey.dicodingstory.data.model.LoginBody
import com.yandey.dicodingstory.databinding.ActivityLoginBinding
import com.yandey.dicodingstory.ui.main.MainActivity
import com.yandey.dicodingstory.ui.register.RegisterActivity
import com.yandey.dicodingstory.util.Constant.USER_TOKEN
import com.yandey.dicodingstory.util.isValidEmail
import com.yandey.dicodingstory.util.setTransparentStatusBar
import com.yandey.dicodingstory.util.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()
    private var job: Job = Job()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        moveToRegisterActivity()
        isFormValid()
        signInButton()
        playAnimation()
        this.setTransparentStatusBar()
    }

    private fun signInButton() {
        binding.apply {
            btnSignIn.setOnClickListener {
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()
                showLoading(true)
                lifecycleScope.launchWhenResumed {
                    if (job.isActive) job.cancel()
                    job = launch {
                        viewModel.login(LoginBody(email, password)).collect { result ->
                            result.onSuccess { response ->
                                response.loginResult.token.let { token ->
                                    viewModel.saveToken(token)
                                    Intent(this@LoginActivity, MainActivity::class.java).apply {
                                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                    }.also { intent ->
                                        intent.putExtra(USER_TOKEN, token)
                                        startActivity(intent)
                                    }
                                }
                                finishAndRemoveTask()
                                this@LoginActivity.toast(response.message)
                                showLoading(false)
                            }
                            result.onFailure {
                                this@LoginActivity.toast(getString(R.string.user_not_found))
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
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            val formCheck =
                (email.isNotEmpty() && email.isValidEmail()) &&
                        (password.isNotEmpty() && password.length >= 6)
            btnSignIn.isEnabled = formCheck
            btnSignIn.setTextColor(resources.getColor(R.color.white_smoke, null))
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

    private fun moveToRegisterActivity() {
        binding.tvSignUp.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
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
            val view1 = ObjectAnimator.ofFloat(tvHi, View.ALPHA, 1f).setDuration(250)
            val view2 = ObjectAnimator.ofFloat(tvGoodDay, View.ALPHA, 1f).setDuration(250)
            val view3 = ObjectAnimator.ofFloat(tvSignInToContinue, View.ALPHA, 1f).setDuration(250)
            val email = ObjectAnimator.ofFloat(etEmail, View.ALPHA, 1f).setDuration(250)
            val password = ObjectAnimator.ofFloat(etPassword, View.ALPHA, 1f).setDuration(250)
            val signIn = ObjectAnimator.ofFloat(btnSignIn, View.ALPHA, 1f).setDuration(250)
            val view4 = ObjectAnimator.ofFloat(tvDontHaveAnAccount, View.ALPHA, 1f).setDuration(250)
            val view5 = ObjectAnimator.ofFloat(tvSignUp, View.ALPHA, 1f).setDuration(250)

            val groupAnimation1 = AnimatorSet().apply {
                playTogether(view1, view2)
            }
            val groupAnimation2 = AnimatorSet().apply {
                playTogether(view4, view5)
            }

            AnimatorSet().apply {
                playSequentially(groupAnimation1, view3, email, password, signIn, groupAnimation2)
                start()
            }
        }
    }

    override fun onBackPressed() {
        startActivity(
            Intent(Intent.ACTION_MAIN)
                .addCategory(Intent.CATEGORY_HOME)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        )
    }
}