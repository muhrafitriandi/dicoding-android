package com.yandey.dicodingstory.data.model.response

data class LoginResult(
    val name: String,
    val token: String,
    val userId: String
)