package com.yandey.dicodingstory.util

import android.util.Patterns
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.yandey.dicodingstory.R

fun String.isValidEmail(): Boolean = Patterns.EMAIL_ADDRESS.matcher(this).matches()
