package com.kotlin.spweartherapp.mvp.view

import android.graphics.Bitmap
import com.kotlin.spweartherapp.mvp.model.CurrentCondition
import kotlinx.coroutines.Deferred

interface IFunDetailPresenter {
    fun getDetail(latLon: String)
    fun getApiDetail(latLon: String):Deferred<List<CurrentCondition>>
    fun getImageWeather(url: String):Deferred<Bitmap?>
}