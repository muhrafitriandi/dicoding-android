package com.yandey.dicodingstory.data.model.response

data class LoginResponse(
    val error: Boolean,
    val loginResult: LoginResult,
    val message: String
)