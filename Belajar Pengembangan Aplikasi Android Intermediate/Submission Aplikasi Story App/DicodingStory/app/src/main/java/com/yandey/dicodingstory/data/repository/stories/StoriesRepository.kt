package com.yandey.dicodingstory.data.repository.stories

import com.yandey.dicodingstory.data.model.NewStoryResponse
import com.yandey.dicodingstory.data.model.StoriesResponse
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface StoriesRepository {
    suspend fun getAllStoriesUser(
        page: Int?,
        size: Int?,
        token: String
    ): Flow<Result<StoriesResponse>>

    suspend fun addNewStory(
        description: RequestBody,
        file: MultipartBody.Part,
        token: String
    ): Flow<Result<NewStoryResponse>>
}