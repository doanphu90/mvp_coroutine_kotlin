package com.kotlin.spweartherapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.kotlin.spweartherapp.Interface.IOnClickListenItem
import com.kotlin.spweartherapp.R
import com.kotlin.spweartherapp.adapter.SearchWeartherAdapter
import com.kotlin.spweartherapp.data.HistoryCity.Companion.savePrefCitySearch
import com.kotlin.spweartherapp.mvp.model.ResultSearch
import com.kotlin.spweartherapp.mvp.presenter.PresenterSearch
import com.kotlin.spweartherapp.mvp.view.IViewMain
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), IViewMain, CoroutineScope {
    private lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private lateinit var adapter: SearchWeartherAdapter
    private lateinit var prsenterSearch: PresenterSearch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        job = Job()
        //
        prsenterSearch = PresenterSearch(coroutineContext, Dispatchers.IO, this)

        keySearch(auto_tv)
        setUpAdapter()
    }

    private fun setUpAdapter() {
//        var list: MutableList<ResultSearch> = arrayListOf()
//        var item = ResultSearch(
//            "Ha Noi", "Viet Nam", "2134e",
//            "413413", "fasdgfa", "vsvfs"
//        )
//        var item1 = ResultSearch(
//            "Sai Gon", "Viet Nam", "2134e",
//            "413413", "fasdgfa", "vsvfs"
//        )
//        var item2 = ResultSearch(
//            "Bac Lieu", "Viet Nam", "2134e",
//            "413413", "fasdgfa", "vsvfs"
//        )
//        list.add(item1)
//        list.add(item)
//        list.add(item2)
        re_View.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter = SearchWeartherAdapter(arrayListOf(), object : IOnClickListenItem {
            override fun onClickItemListener(itemSearch: ResultSearch) {
                Log.d("DoanPhu", "name: ${itemSearch.areaName}")
                //
                savePrefCitySearch(itemSearch)
//                saveHistoryKeyCity(itemSearch)
                //
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra("SelectCity", itemSearch)
                startActivity(intent)
            }
        })
        re_View.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }


    override fun keySearch(searchView: SearchView) {
        searchView.queryHint = getString(R.string.input_text)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
//                prsenterSearch.searchCityByKey(query!!)
                Log.d("DoanPhu", "onQueryTextSubmit")
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    prsenterSearch.getCitySearchHistory()
                } else {
                    prsenterSearch.searchCityByKey(newText!!)
                    Log.d("DoanPhu", "onQueryTextChange")
                }
                return true
            }

        })

        searchView.setOnClickListener {
            searchView.onActionViewExpanded()
            Log.d("DoanPhu", "onActionViewExpanded")
        }

        searchView.setOnCloseListener {
            Log.d("DoanPhu", "setOnCloseListener")
            searchView.onActionViewCollapsed()
            prsenterSearch.getCitySearchHistory()
            true
        }
    }

    override fun fetchResultSearch(listCity: List<ResultSearch>) {
        Log.d("DoanPhu", "List Wearther: ${listCity.size}")
        if (listCity.size > 0) {
            adapter.updateData(listCity)
        } else {
        }
    }

    override fun showTextRecen(isCheck: Boolean) {
        if (isCheck) {
            tv_recentCity.text = getString(R.string.recent_search)
        } else {
            tv_recentCity.text = getString(R.string.not_recent_search)
        }
    }

    override fun getHistorySearchCity(listHis: List<ResultSearch>) {
        if (listHis.size > 0) {
            adapter.updateData(listHis)
            tv_recentCity.text = getString(R.string.recent_search)
        } else {
            tv_recentCity.text = ""
        }

    }

    override fun onResume() {
        super.onResume()
        Log.d("DoanPhu", "onResume")
        auto_tv.setQuery("", false)
        auto_tv.clearFocus()
        if (prsenterSearch != null) {
            prsenterSearch.getCitySearchHistory()
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("DoanPhu", "onResume")

    }
}
