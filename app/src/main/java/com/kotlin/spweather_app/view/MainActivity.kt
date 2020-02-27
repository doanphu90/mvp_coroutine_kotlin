package com.kotlin.spweather_app.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kotlin.spweather_app.Interface.IOnClickListenItem
import com.kotlin.spweather_app.R
import com.kotlin.spweather_app.adapter.SearchWeartherAdapter
import com.kotlin.spweather_app.model.CityDetail
import com.kotlin.spweather_app.model.ResultInfo
import com.kotlin.spweather_app.utils.HistoryCity.Companion.loadFromStorage
import com.kotlin.spweather_app.utils.HistoryCity.Companion.savePrefCitySearch
import com.kotlin.spweather_app.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var adapter: SearchWeartherAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        setUpAdapter()
        searView(sv_Search)

    }

    fun searView(searchView: SearchView) {
        searchView.queryHint = getString(R.string.input_text)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d("DoanPhu", "onQueryTextSubmit")
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                setUpViewModel(newText.toString())
                Log.d("DoanPhu", "onQueryTextChange")
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
            true
        }
    }

    private fun setUpAdapter() {
        re_View.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter = SearchWeartherAdapter(arrayListOf(), object : IOnClickListenItem {
            override fun onClickItemListener(itemSearch: ResultInfo, position:Int) {
                Log.d("DoanPhu", "name: ${itemSearch.areaName}")

                var selectItem = CityDetail(
                    itemSearch.areaName.get(0).value,
                    itemSearch.country.get(0).value,
                    itemSearch.latitude,
                    itemSearch.longitude,
                    itemSearch.population
                )
                savePrefCitySearch(itemSearch, position)
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra("SelectCity", selectItem)
                startActivity(intent)
            }
        })
        re_View.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        sv_Search.setQuery("", false)
        sv_Search.clearFocus()
        if (loadFromStorage().size > 0) {
            tv_recent.text = getString(R.string.recent_search)
            adapter.updateData(loadFromStorage())
        } else {
            tv_recent.text = getString(R.string.not_recent_search)
        }
    }

    fun setUpViewModel(query: String) {
        if (query.isNullOrEmpty()) {
            if (loadFromStorage().size > 0) {
                adapter.updateData(loadFromStorage())
                tv_recent.text = getString(R.string.recent_search)
            } else {
                adapter.updateData(ArrayList())
                tv_recent.text = getString(R.string.not_recent_search)
            }
        } else if (query.length >= 3) {
            tv_recent.text = ""
            homeViewModel.getResultSearch(applicationContext, query)
            homeViewModel.showResultSearch().observe(this, Observer { t ->
                if (t != null) {
                    adapter.updateData(t.searchApi.result)
                }
            })
        }
    }

}
