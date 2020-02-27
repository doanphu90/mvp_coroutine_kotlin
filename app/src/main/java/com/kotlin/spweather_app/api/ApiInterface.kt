package com.kotlin.spweather_app.api

import com.kotlin.spweather_app.model.DetailWeather
import com.kotlin.spweather_app.model.ResulSearch
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {
    @GET("search.ashx?format=json&key=b9c3c03630e545dba9775353201902")
    fun getResultSearch(@Query("query") query: String): Observable<ResulSearch>

    @GET("weather.ashx?format=json&key=b9c3c03630e545dba9775353201902&date=today")
    fun getResultDetailWeather(@Query("q") userId: String): Observable<DetailWeather>
}