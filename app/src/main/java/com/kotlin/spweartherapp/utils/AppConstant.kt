package com.kotlin.spweartherapp.utils

class AppConstant {
    companion object {
        val SHAREDPREFERENCES:String = "MySharedPreference"
        val HISTORY_SEARCH_CITY:String = "HISTORY_SEARCH_CITY"
        val KEY_HISTORY:String = "Key_History"
        //
        private val API_KEY: String = "b9c3c03630e545dba9775353201902"
        val SEARCH_API: String =
            "http://api.worldweatheronline.com/premium/v1/search.ashx?format=json&key=" + API_KEY + "&query="
        val SEARCH_DETAIL_API: String =
            "https://api.worldweatheronline.com/premium/v1/weather.ashx?format=json&key=" + API_KEY + "&date=today&q="
    }
}