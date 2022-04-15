package com.yandey.dicodingstory.domain.usecase

import com.yandey.dicodingstory.data.model.body.LoginBody
import com.yandey.dicodingstory.data.model.response.LoginResponse
import com.yandey.dicodingstory.domain.repository.Repository
import com.yandey.dicodingstory.util.Resource

class LoginUseCase(private val repository: Repository) {
    suspend fun execute(loginBody: LoginBody): Resource<LoginResponse> = repository.getLoginUser(loginBody)
}