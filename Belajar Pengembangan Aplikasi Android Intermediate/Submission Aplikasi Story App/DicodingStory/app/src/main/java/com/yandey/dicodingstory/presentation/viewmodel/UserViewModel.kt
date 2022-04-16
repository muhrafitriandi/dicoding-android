package com.yandey.dicodingstory.presentation.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.yandey.dicodingstory.data.model.body.LoginBody
import com.yandey.dicodingstory.data.model.body.RegisterBody
import com.yandey.dicodingstory.data.model.response.LoginResponse
import com.yandey.dicodingstory.data.model.response.RegisterResponse
import com.yandey.dicodingstory.domain.repository.DataStoreRepository
import com.yandey.dicodingstory.domain.usecase.LoginUseCase
import com.yandey.dicodingstory.domain.usecase.RegisterUseCase
import com.yandey.dicodingstory.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(
    private val app: Application,
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase
): AndroidViewModel(app) {
    val loginUser: MutableLiveData<Resource<LoginResponse>> = MutableLiveData()
    val registerUser: MutableLiveData<Resource<RegisterResponse>> = MutableLiveData()

    private val dataStoreRepository = DataStoreRepository(app)

    fun saveLoginToken(loginToken: String) = viewModelScope.launch {
        dataStoreRepository.saveLoginToken(loginToken)
    }

    fun getLoginToken() = dataStoreRepository.getLoginToken().asLiveData(Dispatchers.IO)

    fun login(loginBody: LoginBody) = viewModelScope.launch {
        loginUser.postValue(Resource.Loading())
        try {
            if (isNetworkAvailable(app)) {
                val response = loginUseCase.execute(loginBody)
                loginUser.postValue(response)
            }else {
                loginUser.postValue(Resource.Error("No internet connection."))
            }
        }catch (e: Exception) {
            loginUser.postValue(Resource.Error(e.message.toString()))
        }
    }

    fun register(registerBody: RegisterBody) = viewModelScope.launch {
        registerUser.postValue(Resource.Loading())
        try {
            if (isNetworkAvailable(app)) {
                val response = registerUseCase.execute(registerBody)
                registerUser.postValue(response)
            }else {
                registerUser.postValue(Resource.Error("No internet connection."))
            }
        }catch (e: Exception) {
            registerUser.postValue(Resource.Error(e.message.toString()))
        }
    }

    private fun isNetworkAvailable(context: Context?): Boolean {
        if (context == null) return false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        return false
    }
}