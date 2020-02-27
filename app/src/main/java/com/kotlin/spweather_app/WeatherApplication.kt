package com.kotlin.spweather_app

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import com.kotlin.spweartherapp.utils.AppConstant

class WeatherApplication : Application() {
    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
        sharePreference = getSharedPreferences(AppConstant.SHAREDPREFERENCES, Context.MODE_PRIVATE)
    }

    companion object {
        lateinit var sharePreference: SharedPreferences
        private var instance: WeatherApplication? = null

        fun applicationContext(): WeatherApplication {
            return instance as WeatherApplication
        }
    }
}