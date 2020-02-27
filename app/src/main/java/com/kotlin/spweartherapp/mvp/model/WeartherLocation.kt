package com.kotlin.spweartherapp.mvp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class WeartherLocation(val searchApi: SearchApi)
data class SearchApi(val liResult: List<ResultSearch>)

@Parcelize
data class ResultSearch(
    val areaName: String,
    val country: String,
    val region: String,
    val latitude: String,
    val longitude: String,
    val population: String
) : Parcelable

data class AreaName(val value: String)
data class Country(val value: String)
data class Region(val value: String)
data class WeatherUrl(val value: String)
