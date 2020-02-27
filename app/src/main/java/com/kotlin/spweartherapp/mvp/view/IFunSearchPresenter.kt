package com.kotlin.spweartherapp.mvp.view

import com.kotlin.spweartherapp.mvp.model.ResultSearch
import com.kotlin.spweartherapp.mvp.model.SearchApi
import kotlinx.coroutines.Deferred
import kotlin.coroutines.CoroutineContext

interface IFunSearchPresenter {
    fun getCitySearchHistory()
    fun searchCityByKey(keyWord: String)
    fun callAPiSearchCity(city: String): Deferred<List<ResultSearch>>
}