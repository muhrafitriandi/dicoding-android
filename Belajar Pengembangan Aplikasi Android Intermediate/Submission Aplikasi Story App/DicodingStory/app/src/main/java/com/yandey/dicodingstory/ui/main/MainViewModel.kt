package com.yandey.dicodingstory.ui.main

import androidx.lifecycle.ViewModel
import com.yandey.dicodingstory.data.repository.stories.StoriesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val storiesRepository: StoriesRepository
) : ViewModel() {
    suspend fun getAllStories(token: String) =
        storiesRepository.getAllStoriesUser(null, null, token)
}