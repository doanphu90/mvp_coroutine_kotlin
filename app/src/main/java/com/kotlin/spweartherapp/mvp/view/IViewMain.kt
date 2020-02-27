package com.kotlin.spweartherapp.mvp.view

import androidx.appcompat.widget.SearchView
import com.kotlin.spweartherapp.mvp.model.ResultSearch
import com.kotlin.spweartherapp.mvp.model.SearchApi

interface IViewMain {
    //view
    fun showTextRecen(isCheck:Boolean)
    //fun logic
    fun getHistorySearchCity(listHis:List<ResultSearch>)
    fun keySearch(searchView: SearchView)
    fun fetchResultSearch(liResult: List<ResultSearch>)
}