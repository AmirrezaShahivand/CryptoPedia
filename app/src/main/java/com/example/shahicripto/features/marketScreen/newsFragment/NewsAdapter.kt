package com.example.shahicripto.features.marketScreen.newsFragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import android.view.View
import com.bumptech.glide.RequestManager
import com.example.shahicripto.databinding.ItemNewsBinding
import com.example.shahicripto.model.local.NewsData.NewsDataEntity
import com.example.shahicripto.model.local.NewsData.NewsDisplayItem

class NewsAdapter(
    private val glide: RequestManager,
    private val recyclerCallback: RecyclerCallback
) : ListAdapter<NewsDisplayItem, NewsAdapter.NewsViewHolder>(DIFF_CALLBACK) {
    private var latestItems: List<NewsDisplayItem> = emptyList()
    inner class NewsViewHolder(private val binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindView(newsData: NewsDisplayItem) {
            binding.txtTitle.text = newsData.title
            binding.txtBody.text = newsData.body
            binding.txtSource.text = newsData.source
            binding.txtTranslationStatus.visibility =
                if (newsData.isTranslating) View.VISIBLE else View.GONE


            glide.load(newsData.original.image)
                .into(binding.imageMainNews)

            binding.root.setOnClickListener {
                recyclerCallback.onItemClicked(newsData.original)
            }


        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bindView(getItem(position))
    }

    fun updateItems(items: List<NewsDisplayItem>) {
        latestItems = items.toList()
        submitList(latestItems)
    }

    fun updateItem(item: NewsDisplayItem) {
        val index = latestItems.indexOfFirst { it.original.url == item.original.url }
        if (index == -1) return
        val updated = latestItems.toMutableList()
        updated[index] = item
        latestItems = updated
        submitList(latestItems)
    }

    fun markTranslating(urls: Set<String>) {
        if (urls.isEmpty()) return
        latestItems = latestItems.map { item ->
            if (item.original.url in urls && !item.isTranslating) {
                item.copy(isTranslating = true)
            } else {
                item
            }
        }
        submitList(latestItems)
    }

    fun itemsSnapshot(): List<NewsDisplayItem> = latestItems

    interface RecyclerCallback {
        fun onItemClicked(dataNews: NewsDataEntity)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<NewsDisplayItem>() {
            override fun areItemsTheSame(oldItem: NewsDisplayItem, newItem: NewsDisplayItem): Boolean {
                return oldItem.original.url == newItem.original.url
            }

            override fun areContentsTheSame(oldItem: NewsDisplayItem, newItem: NewsDisplayItem): Boolean {
                return oldItem == newItem
            }
        }
    }


}
