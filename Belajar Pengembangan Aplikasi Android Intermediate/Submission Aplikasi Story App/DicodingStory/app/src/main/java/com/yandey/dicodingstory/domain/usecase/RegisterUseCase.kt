package com.yandey.dicodingstory.domain.usecase

import com.yandey.dicodingstory.data.model.body.RegisterBody
import com.yandey.dicodingstory.data.model.response.RegisterResponse
import com.yandey.dicodingstory.domain.repository.Repository
import com.yandey.dicodingstory.util.Resource

class RegisterUseCase(private val repository: Repository) {
    suspend fun execute(registerBody: RegisterBody): Resource<RegisterResponse> = repository.getRegisterUser(registerBody)
}