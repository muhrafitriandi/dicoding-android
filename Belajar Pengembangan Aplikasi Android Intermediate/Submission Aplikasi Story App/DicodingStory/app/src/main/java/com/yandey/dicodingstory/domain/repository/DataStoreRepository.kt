package com.yandey.dicodingstory.domain.repository

import android.app.Application
import com.yandey.dicodingstory.data.repository.DataStoreManager

class DataStoreRepository(app: Application) {
    private val dataStoreManager: DataStoreManager = DataStoreManager.getInstance(app)

    suspend fun saveLoginToken(loginToken: String) = dataStoreManager.saveLoginToken(loginToken)
    fun getLoginToken() = dataStoreManager.getLoginToken()
}