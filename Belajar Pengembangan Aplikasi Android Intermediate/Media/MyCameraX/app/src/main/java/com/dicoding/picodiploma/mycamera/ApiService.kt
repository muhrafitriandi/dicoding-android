package com.dicoding.picodiploma.mycamera

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*
import retrofit2.http.POST
import okhttp3.RequestBody
import retrofit2.http.Multipart
interface ApiService {
    @Multipart
    @POST("/v1/stories/guest")
    fun uploadImage(
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
    ): Call<FileUploadResponse>
}