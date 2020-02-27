package com.kotlin.spweartherapp.mvp.presenter

import android.util.Log
import com.kotlin.spweartherapp.data.HistoryCity
import com.kotlin.spweartherapp.mvp.model.ResultSearch
import com.kotlin.spweartherapp.mvp.view.IFunSearchPresenter
import com.kotlin.spweartherapp.mvp.view.IViewMain
import com.kotlin.spweartherapp.utils.AppConstant
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.coroutines.CoroutineContext

class PresenterSearch(
    private val mainContextCo: CoroutineContext,
    private val ioContextCo: CoroutineContext,
    private val viewMain: IViewMain
) : IFunSearchPresenter {
    override fun searchCityByKey(keyWord: String) {
        CoroutineScope(mainContextCo).launch {
            if(keyWord.isNullOrEmpty()) {
                viewMain.showTextRecen(true)
            } else{
                viewMain.showTextRecen(false)
                viewMain.fetchResultSearch(callAPiSearchCity(keyWord).await())
            }
        }

    }

    override fun callAPiSearchCity(
        city: String
    ): Deferred<List<ResultSearch>> {
        return CoroutineScope(ioContextCo).async {
            var resultCity: List<ResultSearch> = listOf()
            var inputStream: InputStream? = null
            var resultJson: String = ""
            val url = URL(AppConstant.SEARCH_API + city)
            val conn: HttpURLConnection = url.openConnection() as HttpURLConnection
            try {
                conn.connect()
                inputStream = conn.inputStream
            } catch (e: Exception) {
                Log.d("DoanPhu", "Err: ${e.message}")
            }
            if (inputStream != null) {
                val bufferedReader: BufferedReader? =
                    BufferedReader(InputStreamReader(inputStream))
                var line: String? = bufferedReader?.readLine()
                while (line != null) {
                    resultJson += line
                    line = bufferedReader?.readLine()
                }
                Log.d("DoanPhu", "Result: $resultJson")
                resultCity = convertJson(resultJson)
            }
            resultCity
        }
    }

    private fun convertJson(resultJson: String?): List<ResultSearch> {
//        var listSearchApi: MutableList<SearchApi> = arrayListOf()
        var listResul: MutableList<ResultSearch> = arrayListOf()
        if (resultJson != null && resultJson.isNotEmpty()) {
            var jsonRoot = JSONObject(resultJson)
            try {
                var jsonObject = jsonRoot.getJSONObject("search_api")
                var jsonArray = jsonObject.getJSONArray("result")
                for (jsonIndex in 0..(jsonArray.length() - 1)) {
                    var areaRoot = jsonArray.getJSONObject(jsonIndex)
                    var area = areaRoot.getJSONArray("areaName")
                    var areaName = area.getJSONObject(0).getString("value")

                    var counRoot = jsonArray.getJSONObject(jsonIndex)
                    var count = counRoot.getJSONArray("country")
                    var country = count.getJSONObject(0).getString("value")

                    var reRoot = jsonArray.getJSONObject(jsonIndex)
                    var re = reRoot.getJSONArray("region")
                    var region = re.getJSONObject(0).getString("value")

                    var latitude: String = jsonArray.getJSONObject(jsonIndex).getString("latitude")
                    var longitude: String =
                        jsonArray.getJSONObject(jsonIndex).getString("longitude")
                    var population: String =
                        jsonArray.getJSONObject(jsonIndex).getString("population")
                    listResul.add(
                        ResultSearch(
                            areaName, country, region, latitude, longitude, population
                        )
                    )
                }
//                listSearchApi.add(SearchApi(listResul))
            } catch (e: Exception) {
                e.message
            }
        }
        return listResul
    }

    override fun getCitySearchHistory() {
        if(HistoryCity.loadFromStorage().size>0) {
            viewMain.showTextRecen(true)
            viewMain.getHistorySearchCity(HistoryCity.loadFromStorage())
        } else{
            viewMain.showTextRecen(false)
        }
    }

}