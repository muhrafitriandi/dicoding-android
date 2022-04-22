package com.yandey.dicodingstory.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.yandey.dicodingstory.data.model.StoriesResult
import com.yandey.dicodingstory.databinding.ItemStoriesBinding
import com.yandey.dicodingstory.ui.detail.DetailActivity
import com.yandey.dicodingstory.util.Constant.EXTRA_USER
import com.yandey.dicodingstory.util.loadImage
import com.yandey.dicodingstory.util.withDateFormat

class StoriesAdapter : RecyclerView.Adapter<StoriesAdapter.StoriesViewHolder>() {
    private val callback = object : DiffUtil.ItemCallback<StoriesResult>() {
        override fun areItemsTheSame(oldItem: StoriesResult, newItem: StoriesResult): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: StoriesResult, newItem: StoriesResult): Boolean =
            oldItem == newItem
    }

    val differ = AsyncListDiffer(this, callback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoriesViewHolder {
        val binding = ItemStoriesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoriesViewHolder(parent.context, binding)
    }

    override fun onBindViewHolder(holder: StoriesViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int = differ.currentList.size

    inner class StoriesViewHolder(
        private val context: Context,
        private val binding: ItemStoriesBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: StoriesResult) {
            binding.apply {
                tvName.text = item.name
                tvStoryDate.withDateFormat(item.createdAt)
                ivStory.loadImage(context, item.photoUrl)
                tvDescription.text = item.description

                itemView.setOnClickListener {
                    val intent =
                        Intent(context, DetailActivity::class.java).putExtra(EXTRA_USER, item)

                    val optionsCompat: ActivityOptionsCompat =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(
                            itemView.context as Activity,
                            Pair(tvName, "name"),
                            Pair(tvStoryDate, "date"),
                            Pair(ivStory, "image"),
                            Pair(tvDescription, "description")
                        )
                    itemView.context.startActivity(intent, optionsCompat.toBundle())
                }
            }
        }
    }
}