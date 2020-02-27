package com.kotlin.spweather_app.api

import android.content.Context
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.kotlin.spweartherapp.utils.AppConstant
import okhttp3.CacheControl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiBuilder {
    private var retrofit: Retrofit? = null
    private const val REQUEST_TIMEOUT = 60 //kieu hang so nhu java static final
    private var okHttpClient: OkHttpClient? = null

    fun getClient(cont: Context): Retrofit? {
        if (okHttpClient == null) initOkhttp(cont)
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(AppConstant.BASE_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        return retrofit
    }

    private fun initOkhttp(cont: Context) {
        val httpClient = OkHttpClient().newBuilder()
            .connectTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .readTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .writeTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)

        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        httpClient.addInterceptor(interceptor)

        httpClient.addInterceptor{ chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
            val request = requestBuilder.cacheControl(CacheControl.FORCE_NETWORK).build()
            chain.proceed(request)
        }
        okHttpClient = httpClient.build()
    }
}