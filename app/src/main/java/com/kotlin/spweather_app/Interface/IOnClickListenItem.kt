package com.kotlin.spweather_app.Interface

import com.kotlin.spweather_app.model.ResultInfo

interface IOnClickListenItem {
    fun onClickItemListener(item: ResultInfo, position:Int)
}