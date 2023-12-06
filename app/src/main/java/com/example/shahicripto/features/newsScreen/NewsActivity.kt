package com.example.shahicripto.features.newsScreen

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.bumptech.glide.RequestManager
import com.example.shahicripto.R
import com.example.shahicripto.databinding.ActivityNewsBinding
import com.example.shahicripto.model.local.CoinsData.CoinsDataEntitity
import com.example.shahicripto.model.local.NewsData.NewsDataEntity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NewsActivity : AppCompatActivity() {
    lateinit var binding: ActivityNewsBinding
    lateinit var dataThisNews: NewsDataEntity


    @Inject
    lateinit var glide: RequestManager

    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityNewsBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarActivity2)
        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        binding.collapsingMain.setExpandedTitleColor(
            ContextCompat.getColor(
                this,
                android.R.color.transparent
            )
        )

        val fromIntent = intent.getBundleExtra("bundle")!!

        dataThisNews = fromIntent.getParcelable("bundle1")!!

        binding.txtDetailText.text = dataThisNews.body
        binding.txtDetailTitle.text = dataThisNews.title
        glide.load(dataThisNews.image).into(binding.imgActivity2Main)

        binding.fabOpenWikipedia.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(dataThisNews.url))
            startActivity(intent)
        }

    }



}