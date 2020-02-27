package com.kotlin.spweartherapp.mvp.view

import android.view.View
import com.kotlin.spweartherapp.mvp.model.CurrentCondition

interface IViewDetail {
    fun showHidePro(isChecked: Boolean)
    fun fetchDetail(cur: List<CurrentCondition>)
}