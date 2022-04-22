package com.yandey.dicodingstory.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class StoriesResponse(
    val error: Boolean,
    val listStory: List<StoriesResult>,
    val message: String
)

@Parcelize
data class StoriesResult(
    val createdAt: String,
    val description: String,
    val id: String,
    val lat: Double,
    val lon: Double,
    val name: String,
    val photoUrl: String
) : Parcelable