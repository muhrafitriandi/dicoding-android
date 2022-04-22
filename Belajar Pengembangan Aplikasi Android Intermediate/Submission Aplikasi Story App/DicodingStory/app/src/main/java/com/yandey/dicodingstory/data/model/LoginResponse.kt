package com.yandey.dicodingstory.data.model

data class LoginResponse(
    val error: Boolean,
    val loginResult: LoginResult,
    val message: String
)

data class LoginResult(
    val name: String,
    val token: String,
    val userId: String
)

data class LoginBody(
    val email: String,
    val password: String
)