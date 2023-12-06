package com.example.shahicripto.features.marketScreen.newsFragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.shahicripto.databinding.ItemNewsBinding
import com.example.shahicripto.model.local.NewsData.NewsDataEntity

class NewsAdapter(
    private val glide: RequestManager,
    private val data: ArrayList<NewsDataEntity>,
    private val recyclerCallback: RecyclerCallback
) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {
    lateinit var binding: ItemNewsBinding

    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindView(newsData: NewsDataEntity) {
            binding.txtTitle.text = newsData.title
            binding.txtBody.text = newsData.body


            glide.load(newsData.image)
                .into(binding.imageMainNews)

            itemView.setOnClickListener {
                recyclerCallback.onItemClicked(newsData)
            }


        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return NewsViewHolder(binding.root)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.setIsRecyclable(false)
        holder.bindView(data[position])
    }


    interface RecyclerCallback {
        fun onItemClicked(dataNews: NewsDataEntity)
    }


}