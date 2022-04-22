package com.yandey.dicodingstory.data.model

data class RegisterResponse(
    val error: Boolean,
    val message: String
)

data class RegisterBody(
    val name: String,
    val email: String,
    val password: String
)