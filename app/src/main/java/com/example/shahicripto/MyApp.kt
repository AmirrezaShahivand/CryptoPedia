package com.example.shahicripto

import android.app.Application
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase

class MyApp : Application() {
    private lateinit var analytics: FirebaseAnalytics

    override fun onCreate() {
        super.onCreate()
        analytics = Firebase.analytics
    }
}