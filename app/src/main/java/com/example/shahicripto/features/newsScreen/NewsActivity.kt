package com.example.shahicripto.features.newsScreen

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.bumptech.glide.RequestManager
import com.example.shahicripto.R
import com.example.shahicripto.MyApp
import com.example.shahicripto.databinding.ActivityNewsBinding
import com.example.shahicripto.model.local.CoinsData.CoinsDataEntitity
import com.example.shahicripto.model.local.NewsData.NewsDataEntity
import com.example.shahicripto.model.local.NewsData.NewsDisplayItem
import com.example.shahicripto.util.NewsTranslator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable

@AndroidEntryPoint
class NewsActivity : AppCompatActivity() {
    lateinit var binding: ActivityNewsBinding
    lateinit var dataThisNews: NewsDataEntity
    private val newsTranslator: NewsTranslator
        get() = (applicationContext as MyApp).newsTranslator
    private val disposables = CompositeDisposable()


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

        val fromIntent = intent.getBundleExtra("bundle")
        val news = fromIntent?.getSerializable("bundle1") as? NewsDataEntity
        if (news == null) {
            finish()
            return
        }
        dataThisNews = news

        binding.txtDetailText.text = dataThisNews.body
        binding.txtDetailTitle.text = dataThisNews.title
        binding.txtDetailSource.text = dataThisNews.url.substringAfter("//").substringBefore("/")
        glide.load(dataThisNews.image).into(binding.imgActivity2Main)

        newsTranslator
            .display(dataThisNews, resources.configuration.locales[0].language)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ display ->
                binding.txtDetailTitle.text = display.title
                binding.txtDetailText.text = display.body
                binding.txtDetailSource.text = display.source
            }, { /* original content remains visible */ })
            .also(disposables::add)

        binding.fabOpenWikipedia.setOnClickListener {
            val uri = Uri.parse(dataThisNews.url)
            if (uri.scheme == "http" || uri.scheme == "https") {
                val intent = Intent(Intent.ACTION_VIEW, uri)
                if (intent.resolveActivity(packageManager) != null) startActivity(intent)
            }
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun onDestroy() {
        disposables.clear()
        super.onDestroy()
    }



}
