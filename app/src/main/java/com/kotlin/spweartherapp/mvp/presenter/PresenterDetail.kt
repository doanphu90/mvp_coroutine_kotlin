package com.kotlin.spweartherapp.mvp.presenter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.kotlin.spweartherapp.mvp.model.CurrentCondition
import com.kotlin.spweartherapp.mvp.view.IFunDetailPresenter
import com.kotlin.spweartherapp.mvp.view.IViewDetail
import com.kotlin.spweartherapp.utils.AppConstant
import kotlinx.coroutines.*
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.coroutines.CoroutineContext


class PresenterDetail(
    private val mainContextCo: CoroutineContext,
    private val ioContextCo: CoroutineContext,
    private val iViewDetail: IViewDetail
) : IFunDetailPresenter {
    override fun getDetail(latLon: String) {
//        val handler = CoroutineExceptionHandler { mainContextCo, exception ->
//            println("Caught $exception with suppressed ${exception.suppressed.contentToString()}")
//        }
        CoroutineScope(mainContextCo).launch() {
            iViewDetail.fetchDetail(getApiDetail(latLon).await())
        }
    }

    override fun getApiDetail(latLon: String): Deferred<List<CurrentCondition>> {
        return CoroutineScope(ioContextCo).async {
            var resultCondition: List<CurrentCondition> = listOf()
            var inputStream: InputStream? = null
            var resultJson: String = ""
            val url = URL(AppConstant.SEARCH_DETAIL_API + latLon)
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
                resultCondition = convertJson(resultJson)
            }
            resultCondition
        }
    }

    private fun convertJson(resultJson: String): List<CurrentCondition> {
        var listResul: MutableList<CurrentCondition> = arrayListOf()
        if (resultJson != null && resultJson.isNotEmpty()) {
            var jsonRoot = JSONObject(resultJson)
            try {
                var jsonObject = jsonRoot.getJSONObject("data")
                var jsonArray = jsonObject.getJSONArray("current_condition")
                for (jsonIndex in 0..(jsonArray.length() - 1)) {
                    var observation =
                        jsonArray.getJSONObject(jsonIndex).getString("observation_time")
                    var temp_C = jsonArray.getJSONObject(jsonIndex).getString("temp_C")
                    var temp_F = jsonArray.getJSONObject(jsonIndex).getString("temp_F")
                    var weatherCode = jsonArray.getJSONObject(jsonIndex).getString("weatherCode")

                    var urlRoot = jsonArray.getJSONObject(jsonIndex)
                    var icUrl = urlRoot.getJSONArray("weatherIconUrl")
                    var weatherIconUrl = icUrl.getJSONObject(0).getString("value")

                    var descRoot = jsonArray.getJSONObject(jsonIndex)
                    var desc = urlRoot.getJSONArray("weatherDesc")
                    var weatherDesc = icUrl.getJSONObject(0).getString("value")

                    var windspeedMiles: String =
                        jsonArray.getJSONObject(jsonIndex).getString("windspeedMiles")
                    var windspeedKmph: String =
                        jsonArray.getJSONObject(jsonIndex).getString("windspeedKmph")
                    var winddirDegree: String =
                        jsonArray.getJSONObject(jsonIndex).getString("winddirDegree")
                    var winddir16Point: String =
                        jsonArray.getJSONObject(jsonIndex).getString("winddir16Point")
                    var precipMM: String = jsonArray.getJSONObject(jsonIndex).getString("precipMM")
                    var precipInches: String =
                        jsonArray.getJSONObject(jsonIndex).getString("precipInches")
                    var humidity: String = jsonArray.getJSONObject(jsonIndex).getString("humidity")

                    listResul.add(
                        CurrentCondition(
                            observation,
                            temp_C,
                            temp_F,
                            weatherCode,
                            weatherIconUrl,
                            weatherDesc,
                            windspeedMiles,
                            windspeedKmph,
                            winddirDegree,
                            winddir16Point,
                            precipMM,
                            precipInches,
                            humidity
                        )
                    )
                }
            } catch (e: Exception) {
                e.message
            }
        }
        return listResul
    }

    override fun getImageWeather(url: String): Deferred<Bitmap?> {
        return CoroutineScope(ioContextCo).async {
            var img: Bitmap?=null
            if (url.isNotEmpty()) {
                    img = decodeImage(url)
            }
            img
        }
    }

    fun decodeImage(urlImg: String): Bitmap {
        var img: Bitmap? = null
        try {
            val input: InputStream = URL(urlImg).openStream()
            img = BitmapFactory.decodeStream(input)
        } catch (e: java.lang.Exception) {
            Log.e("Error", e.message)
            e.printStackTrace()
        }
        return img!!
    }

}