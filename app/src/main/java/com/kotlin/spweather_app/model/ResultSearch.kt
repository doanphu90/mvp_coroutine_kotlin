package com.kotlin.spweather_app.model

import com.google.gson.annotations.SerializedName

data class ResulSearch (
    @SerializedName("search_api")
    val searchApi: SearchApi
)

data class SearchApi(
    val result: List<ResultInfo>
)

data class ResultInfo(
    val areaName: List<AreaName>,
    val country: List<Country>,
    val latitude: String,
    val longitude: String,
    val population: String
//    val region: List<Region>,
//    val weatherUrl: List<WeatherUrl>
)

data class AreaName(
    val value: String
)

data class Country(
    val value: String
)

data class Region(
    val value: String
)

data class WeatherUrl(
    val value: String
)