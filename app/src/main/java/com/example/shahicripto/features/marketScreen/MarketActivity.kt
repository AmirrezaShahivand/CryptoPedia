package com.example.shahicripto.features.marketScreen

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import cn.pedant.SweetAlert.SweetAlertDialog
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.RequestManager
import com.example.shahicripto.R
import com.example.shahicripto.util.NetworkChecker
import com.example.shahicripto.model.MainRepository
import com.example.shahicripto.databinding.ActivityMarketBinding
import com.example.shahicripto.features.marketScreen.marketFragment.MarketFragment
import com.example.shahicripto.features.marketScreen.newsFragment.NewsFragment
import com.example.shahicripto.model.MyDatabase
import com.example.shahicripto.model.api.ApiService
import com.example.shahicripto.util.MarketViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle
import javax.inject.Inject

@AndroidEntryPoint
class MarketActivity : AppCompatActivity() {
    lateinit var marketScreenViewModel: MarketScreenViewModel

    @Inject
    lateinit var glide: RequestManager

    @Inject
    lateinit var apiService: ApiService
lateinit var binding : ActivityMarketBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMarketBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.layoutToolbar.toolbar)

        firstRun()
        bottomNavigation()
        internetChecker(this)


        binding.swipeRefreshMain.setOnRefreshListener {
            marketScreenViewModel.refreshData()
            marketScreenViewModel.refreshNews()
            internetChecker(this)
            Handler(Looper.getMainLooper()).postDelayed({
                binding.swipeRefreshMain.isRefreshing = false
            }, 1500)
        }

        marketScreenViewModel = ViewModelProvider(
            this, MarketViewModelFactory(
                MainRepository(
                    apiService,
                    MyDatabase.getDatabase(this).coinsDataDao,
                    MyDatabase.getDatabase(this).newsDataDao
                )
            )
        ).get(MarketScreenViewModel::class.java)


        binding.layoutToolbar.info.setOnClickListener {
            val dialog = SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE)
            dialog.titleText = "About developer"
            dialog.contentText =
                "<< Amirreza Shahivand >>  gmail : shahivandamirreza@gmail.com \n github : AmirrezaShahivand"
            dialog.confirmText = "OK :)"
            dialog.show()

            dialog.setConfirmClickListener {
                dialog.dismiss()
            }
        }



    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_main, fragment)
        transaction.commit()
    }

    private fun firstRun() {
        // setActionBar(binding.toolBarMain)
        replaceFragment(MarketFragment())
        binding.bottomNavigationView.selectedItemId = R.id.menu_market


    }

    private fun bottomNavigation() {

        binding.bottomNavigationView.setOnItemSelectedListener {

            when(it.itemId) {

                R.id.menu_market -> {
                    replaceFragment(MarketFragment())
                }

                R.id.menu_news -> {
                    replaceFragment(NewsFragment())
                }


            }
            true
        }

    }

    private fun internetChecker(context: Context) {
        if (NetworkChecker(context).isInternetConnected) {

        } else {
            //    Toast.makeText(this, "لطفا اینترنت خود را متصل نمایید!", Toast.LENGTH_SHORT).show()

            Handler(Looper.getMainLooper()).postDelayed({
                MotionToast.darkColorToast(
                    this, "connection failed!",
                    "لطفا اینترنت خود را متصل نمایید",
                    MotionToastStyle.ERROR,
                    MotionToast.GRAVITY_BOTTOM,
                    MotionToast.LONG_DURATION,
                    ResourcesCompat.getFont(this, www.sanju.motiontoast.R.font.helvetica_regular)
                )
            }, 1000)


        }
    }




}