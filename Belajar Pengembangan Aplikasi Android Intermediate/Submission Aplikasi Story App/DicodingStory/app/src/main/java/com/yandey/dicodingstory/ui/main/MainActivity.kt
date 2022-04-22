package com.yandey.dicodingstory.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yandey.dicodingstory.R
import com.yandey.dicodingstory.adapter.StoriesAdapter
import com.yandey.dicodingstory.databinding.ActivityMainBinding
import com.yandey.dicodingstory.ui.addstory.AddStoryActivity
import com.yandey.dicodingstory.ui.settings.SettingsActivity
import com.yandey.dicodingstory.util.Constant.USER_TOKEN
import com.yandey.dicodingstory.util.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var token: String
    private lateinit var storiesAdapter: StoriesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.mainToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        token = intent.getStringExtra(USER_TOKEN).toString()
        initRecyclerView(binding.rvStories)
        getAllStories()
        moveToAddNewStoryActivity()
    }

    private fun getAllStories() {
        showLoading(true)
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.getAllStories(token).collect { result ->
                    result.onSuccess { response ->
                        storiesAdapter.differ.submitList(response.listStory)
                        showLoading(false)
                    }
                    result.onFailure {
                        this@MainActivity.toast(getString(R.string.something_is_wrong))
                        showLoading(false)
                    }
                }
            }
        }
    }

    private fun initRecyclerView(recyclerView: RecyclerView) {
        recyclerView.apply {
            storiesAdapter = StoriesAdapter()
            adapter = storiesAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    private fun moveToAddNewStoryActivity() {
        binding.fabAddNewStory.setOnClickListener {
            startActivity(Intent(this@MainActivity, AddStoryActivity::class.java))
        }
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_option, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            R.id.menuSetting -> {
                startActivity(Intent(this@MainActivity, SettingsActivity::class.java))
                true
            }
            else -> false
        }

    override fun onBackPressed() {
        startActivity(
            Intent(Intent.ACTION_MAIN)
                .addCategory(Intent.CATEGORY_HOME)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        )
    }
}