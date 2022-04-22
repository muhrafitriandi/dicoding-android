package com.yandey.dicodingstory.data.repository.stories

import com.yandey.dicodingstory.data.model.NewStoryResponse
import com.yandey.dicodingstory.data.model.StoriesResponse
import com.yandey.dicodingstory.data.remote.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.lang.Exception
import javax.inject.Inject

class StoriesRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : StoriesRepository {
    override suspend fun getAllStoriesUser(
        page: Int?,
        size: Int?,
        token: String
    ): Flow<Result<StoriesResponse>> = flow {
        try {
            val response = apiService.getAllStories(page, size, token = "Bearer $token")
            emit(Result.success(response))
        } catch (exception: Exception) {
            exception.printStackTrace()
            emit(Result.failure(exception))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun addNewStory(
        description: RequestBody,
        file: MultipartBody.Part,
        token: String
    ): Flow<Result<NewStoryResponse>> = flow {
        try {
            val response = apiService.addNewStory(description, file, token = "Bearer $token")
            emit(Result.success(response))
        } catch (exception: Exception) {
            exception.printStackTrace()
            emit(Result.failure(exception))
        }
    }
}