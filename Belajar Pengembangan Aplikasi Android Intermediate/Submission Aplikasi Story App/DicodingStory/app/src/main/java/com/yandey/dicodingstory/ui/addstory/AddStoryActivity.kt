package com.yandey.dicodingstory.ui.addstory

import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.FileProvider
import androidx.lifecycle.lifecycleScope
import com.yandey.dicodingstory.R
import com.yandey.dicodingstory.databinding.ActivityAddStoryBinding
import com.yandey.dicodingstory.util.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

@AndroidEntryPoint
class AddStoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddStoryBinding
    private lateinit var currentPhotoPath: String
    private var getFile: File? = null
    private lateinit var token: String
    private val viewModel: AddStoryViewModel by viewModels()

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            val file = File(currentPhotoPath)

            getFile = file
            val result = BitmapFactory.decodeFile(getFile?.path)
            binding.ivPreviewImage.setImageBitmap(result)
        }
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            val selectedImg: Uri = it.data?.data as Uri
            val file = uriToFile(selectedImg, this@AddStoryActivity)

            getFile = file
            binding.ivPreviewImage.setImageURI(selectedImg)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initToken()

        binding.apply {
            btnCamera.setOnClickListener {
                startIntentCamera()
            }

            btnGallery.setOnClickListener {
                startGallery()
            }
            fabUploadStory.setOnClickListener {
                uploadStory()
            }
        }

        backToActivity()
    }

    private fun uploadStory() {
        if (binding.etDescription.text.toString().isEmpty() || getFile == null) {
            this@AddStoryActivity.toast(getString(R.string.warning_message_new_story))
        } else {
            showLoading(true)
            val file = reduceFileImage(getFile as File)
            val description =
                binding.etDescription.text.toString().toRequestBody("text/plain".toMediaType())
            val requestImageFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "photo",
                file.name,
                requestImageFile
            )
            lifecycleScope.launchWhenCreated {
                launch {
                    viewModel.addNewStory(description, imageMultipart, token).collect { result ->
                        result.onSuccess { response ->
                            this@AddStoryActivity.toast(response.message)
                            finishAndRemoveTask()
                            showLoading(false)
                        }
                        result.onFailure {
                            this@AddStoryActivity.toast(getString(R.string.failure))
                            showLoading(false)
                        }
                    }
                }
            }
        }
    }

    private fun initToken() {
        lifecycleScope.launchWhenCreated {
            launch {
                viewModel.getToken().collect { tokenTemp ->
                    if (!tokenTemp.isNullOrEmpty()) token = tokenTemp
                }
            }
        }
    }

    private fun startIntentCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
            resolveActivity(packageManager)
        }
        createCustomTempFile(application).also {
            val photoURI: Uri = FileProvider.getUriForFile(
                this@AddStoryActivity,
                "com.yandey.dicodingstory",
                it
            )
            currentPhotoPath = it.absolutePath
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            launcherIntentCamera.launch(intent)
        }
    }

    private fun startGallery() {
        val intent = Intent().apply {
            action = ACTION_GET_CONTENT
            type = "image/*"
        }
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            if (isLoading) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.INVISIBLE
            }
        }
    }

    private fun backToActivity() {
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
    }
}