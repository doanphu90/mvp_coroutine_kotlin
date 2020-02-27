package com.kotlin.spweartherapp.data

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.text.TextUtils
import android.util.Log
import com.kotlin.spweartherapp.WeartherApplication
import com.kotlin.spweartherapp.mvp.model.ResultSearch
import com.kotlin.spweartherapp.utils.AppConstant
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject


class HistoryCity {
    companion object {
        fun saveHistoryKeyCity(item: ResultSearch) {
            var editor = WeartherApplication.sharePreference.edit()
            var listData: MutableList<ResultSearch> = arrayListOf()
            listData.add(item)
            //
            var keyHis: String? = WeartherApplication.sharePreference
                .getString(AppConstant.KEY_HISTORY, "")
            if (keyHis.isNullOrEmpty()) {
                keyHis = ""
            }


            var joins: String = TextUtils.join(", ", listData)
            Log.d("DoanPhu", "Convert String: " + joins)


        }

        fun savePrefCitySearch(item: ResultSearch) {
            var editor = WeartherApplication.sharePreference.edit()

            var jsonObject = getJsonObject(item)
            var set: MutableSet<String>? = checkExistKey()
            if (set.isNullOrEmpty()) {
                set = hashSetOf()
            }
//            var items = JSONArray()
//            items.put(jsonObject)
//            for (i in 0 until items.length()) {
            set.add(jsonObject.toString())
//            }
            editor.clear();
            editor.putStringSet(AppConstant.HISTORY_SEARCH_CITY, HashSet(set))
            editor.commit()
            Log.d("DoanPhu", "Put Data: ${set.size} - commit preference: ${editor.commit()}")
        }

        //Exist set
        fun checkExistKey(): MutableSet<String>? {
            var set: MutableSet<String>? =
                WeartherApplication.sharePreference.getStringSet(
                    AppConstant.HISTORY_SEARCH_CITY,
                    HashSet()
                )
            Log.d("DoanPhu", "Check Data have save: ${set?.size}")
            return set
        }

        fun getJsonObject(item: ResultSearch): JSONObject {
            var obj = JSONObject()
            try {
                obj.put("areaName", item.areaName)
                obj.put("country", item.country)
                obj.put("region", item.region)
                obj.put("latitude", item.latitude)
                obj.put("longitude", item.longitude)
                obj.put("population", item.population)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return obj
        }

        //
        fun loadFromStorage(): ArrayList<ResultSearch> {
            var items: ArrayList<ResultSearch> = ArrayList<ResultSearch>();

            var set: MutableSet<String>? = WeartherApplication.sharePreference.getStringSet(
                AppConstant.HISTORY_SEARCH_CITY,
                HashSet()
            )
            Log.d("DoanPhu", "get data from preference: ${set?.size}")
            if (set != null) {
                for (s in set) {
                    try {
                        var jsonObject: JSONObject = JSONObject(s)
                        var areaName = jsonObject.getString("areaName")
                        var country = jsonObject.getString("country")
                        var region = jsonObject.getString("region")
                        var latitude = jsonObject.getString("latitude")
                        var longitude = jsonObject.getString("longitude")
                        var population = jsonObject.getString("population")
                        var itemSearch =
                            ResultSearch(areaName, country, region, latitude, longitude, population)

                        items.add(itemSearch)

                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
            }
            return items
        }
    }
    /*val strings: MutableList<String> = ArrayList(loadFromStorage().size)
            for (items in loadFromStorage()) {
                strings.add(Objects.toString(items, null))
            }
            Log.d("DoanPhu", "Convert String: $strings")

            val objectList: MutableList<ResultInfo> = ArrayList<ResultInfo>(strings.toList())*/
}
