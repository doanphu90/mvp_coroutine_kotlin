package com.kotlin.spweartherapp

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.kotlin.spweartherapp.utils.AppConstant

class WeartherApplication : Application() {
    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
        sharePreference = getSharedPreferences(AppConstant.SHAREDPREFERENCES, Context.MODE_PRIVATE)
    }

    companion object {
        lateinit var sharePreference: SharedPreferences
        private var instance: WeartherApplication? = null

        fun applicationContext(): WeartherApplication {
            return instance as WeartherApplication
        }
    }

}