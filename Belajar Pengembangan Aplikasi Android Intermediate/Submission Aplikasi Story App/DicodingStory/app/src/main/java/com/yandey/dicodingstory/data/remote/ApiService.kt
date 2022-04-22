package com.yandey.dicodingstory.data.remote

import com.yandey.dicodingstory.data.model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiService {
    @POST("login")
    suspend fun login(@Body loginBody: LoginBody): LoginResponse

    @POST("register")
    suspend fun register(@Body registerBody: RegisterBody): RegisterResponse

    @GET("stories")
    suspend fun getAllStories(
        @Query("page") page: Int?,
        @Query("size") size: Int?,
        @Header("Authorization") token: String,
    ): StoriesResponse

    @Multipart
    @POST("stories")
    suspend fun addNewStory(
        @Part("description") description: RequestBody,
        @Part file: MultipartBody.Part,
        @Header("Authorization") token: String
    ): NewStoryResponse
}