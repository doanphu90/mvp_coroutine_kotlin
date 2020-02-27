package com.kotlin.spweather_app.viewmodel

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.MutableLiveData
import com.kotlin.spweather_app.api.ApiBuilder
import com.kotlin.spweather_app.api.ApiInterface
import com.kotlin.spweather_app.base.BaseViewModel
import com.kotlin.spweather_app.model.DetailWeather
import com.kotlin.spweather_app.model.ResulSearch
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import java.io.InputStream
import java.net.URL
import java.net.URLEncoder
import java.util.*


class HomeViewModel : BaseViewModel() {
    private val searchResponse = MutableLiveData<ResulSearch>()
    private val detailSelect = MutableLiveData<DetailWeather>()

    fun showResultSearch(): MutableLiveData<ResulSearch> {
        return searchResponse
    }

    fun showResultWeather(): MutableLiveData<DetailWeather> {
        return detailSelect
    }

    fun getResultSearch(mContext: Context, query: String) {
        val apiService = ApiBuilder.getClient(mContext)?.create(ApiInterface::class.java)
        val queryEncode = URLEncoder.encode(query, "UTF-8")
        disposables.add(
            apiService?.getResultSearch(queryEncode)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({
                    searchResponse.value = it
                    Log.d("DoanPhu", "searchResponse: " + searchResponse.value)
                }, {
                    showFailure(it)
                })!!
        )
    }

    fun getResultDetailWeather(mContext: Context, latLong: String) {
        val apiService = ApiBuilder.getClient(mContext)?.create(ApiInterface::class.java)
        disposables.add(
            apiService?.getResultDetailWeather(latLong)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({
                    detailSelect.value = it
                }, { showFailure(it) })!!
        )
    }

    fun getImageWeather(url: String): Deferred<Bitmap?> {
        return CoroutineScope(Dispatchers.IO).async {
            var img: Bitmap?=null
            if (url.isNotEmpty()) {
                img = decodeImage(url)
            }
            img
        }
    }

    private fun decodeImage(urlImg: String): Bitmap {
        var img: Bitmap? = null
        try {
            val input: InputStream = URL(urlImg).openStream()
            img = BitmapFactory.decodeStream(input)
        } catch (e: java.lang.Exception) {
            Log.e("Error", e.message)
            e.printStackTrace()
        }
        return img!!
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("HomeViewModel", "onCleared")
        disposables.clear()
    }
}