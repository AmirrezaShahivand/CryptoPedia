package com.example.shahicripto.features.marketScreen

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.fragment.app.Fragment
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.shahicripto.R
import com.example.shahicripto.databinding.ActivityMarketBinding
import com.example.shahicripto.features.marketScreen.marketFragment.MarketFragment
import com.example.shahicripto.features.marketScreen.newsFragment.NewsFragment
import com.example.shahicripto.util.NetworkChecker
import dagger.hilt.android.AndroidEntryPoint
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle


@AndroidEntryPoint
class MarketActivity : AppCompatActivity() {
    lateinit var sharePreferences: SharedPreferences

    var firstRun = true

    lateinit var binding: ActivityMarketBinding
    private var initialTabId: Int = R.id.menu_market
    @SuppressLint("WrongThread")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMarketBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.layoutToolbar.toolbar)
        binding.layoutToolbar.language.text = if (resources.configuration.locales[0].language == "fa") "FA" else "EN"
        initialTabId = savedInstanceState?.getInt(SELECTED_TAB_KEY, R.id.menu_market) ?: R.id.menu_market

        sharePreferences = getSharedPreferences("data", Context.MODE_PRIVATE)



       val first = sharePreferences.getBoolean("firstRun" , true)

        if (first){
            firstRun = false
            sharePreferences.edit().putBoolean("firstRun" , firstRun).apply()
            val dialog = SweetAlertDialog(this, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
            dialog.titleText = getString(R.string.welcome_title)
            dialog.contentText = getString(R.string.welcome_message)
            dialog.confirmText = getString(R.string.thanks)
            dialog.setCustomImage(R.drawable.welcome)
            dialog.show()

            dialog.setConfirmClickListener {
                dialog.dismiss()
            }
        }

        



        firstRun()
        bottomNavigation()
        internetChecker(this)


        binding.swipeRefreshMain.setOnRefreshListener {
            internetChecker(this)
            Handler(Looper.getMainLooper()).postDelayed({
                binding.swipeRefreshMain.isRefreshing = false
            }, 1500)
        }


        binding.layoutToolbar.info.setOnClickListener {
            val dialog = SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE)
            dialog.titleText = getString(R.string.about_developer)
            dialog.contentText = getString(R.string.about_developer_text)
            dialog.confirmText = getString(R.string.ok)
            dialog.show()

            dialog.setConfirmClickListener {
                dialog.dismiss()
            }
        }

        binding.layoutToolbar.language.setOnClickListener { showLanguageDialog() }
        binding.layoutToolbar.themeToggle.setOnClickListener { toggleTheme() }



    }

    private fun toggleTheme() {
        val isNight = (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES
        AppCompatDelegate.setDefaultNightMode(
            if (isNight) AppCompatDelegate.MODE_NIGHT_NO else AppCompatDelegate.MODE_NIGHT_YES
        )
    }

    private fun showLanguageDialog() {
        val languages = arrayOf(getString(R.string.language_persian), getString(R.string.language_english))
        androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle(R.string.change_language)
            .setItems(languages) { _, which ->
                val tag = if (which == 0) "fa" else "en"
                AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(tag))
            }
            .show()
    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_main, fragment)
        transaction.commit()
    }

    private fun firstRun() {
        replaceFragment(if (initialTabId == R.id.menu_news) NewsFragment() else MarketFragment())
        binding.bottomNavigationView.selectedItemId = initialTabId


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

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(SELECTED_TAB_KEY, binding.bottomNavigationView.selectedItemId)
        super.onSaveInstanceState(outState)
    }

    companion object {
        private const val SELECTED_TAB_KEY = "selected_tab"
    }

}
