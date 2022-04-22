package com.yandey.dicodingstory.ui.addstory

import androidx.lifecycle.ViewModel
import com.yandey.dicodingstory.data.model.NewStoryResponse
import com.yandey.dicodingstory.data.repository.auth.AuthRepository
import com.yandey.dicodingstory.data.repository.stories.StoriesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class AddStoryViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val storiesRepository: StoriesRepository
) : ViewModel() {

    fun getToken(): Flow<String?> = authRepository.getToken()

    suspend fun addNewStory(
        description: RequestBody,
        file: MultipartBody.Part,
        token: String
    ): Flow<Result<NewStoryResponse>> = storiesRepository.addNewStory(description, file, token)
}