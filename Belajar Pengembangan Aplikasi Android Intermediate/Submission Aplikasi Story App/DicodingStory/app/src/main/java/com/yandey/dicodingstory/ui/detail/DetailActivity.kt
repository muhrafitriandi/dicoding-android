package com.yandey.dicodingstory.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yandey.dicodingstory.data.model.StoriesResult
import com.yandey.dicodingstory.databinding.ActivityDetailBinding
import com.yandey.dicodingstory.util.Constant.EXTRA_USER
import com.yandey.dicodingstory.util.loadImage
import com.yandey.dicodingstory.util.setTransparentStatusBar
import com.yandey.dicodingstory.util.withDateFormat

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val item = intent.getParcelableExtra<StoriesResult>(EXTRA_USER) as StoriesResult

        catchData(item)
        this.setTransparentStatusBar()
        backToActivity()
    }

    private fun catchData(storiesResult: StoriesResult) {
        binding.apply {
            tvName.text = storiesResult.name
            tvStoryDate.withDateFormat(storiesResult.createdAt)
            ivStory.loadImage(this@DetailActivity, storiesResult.photoUrl)
            tvDescription.text = storiesResult.description
        }
    }

    private fun backToActivity() {
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
    }
}